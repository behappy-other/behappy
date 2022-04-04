package org.xiaowu.behappy.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.ware.config.MqProperties;
import org.xiaowu.behappy.ware.dto.WareSkuLockDto;
import org.xiaowu.behappy.ware.dto.WareSkuLockItemDto;
import org.xiaowu.behappy.ware.entity.WareOrderTaskDetailEntity;
import org.xiaowu.behappy.ware.entity.WareSkuEntity;
import org.xiaowu.behappy.ware.mapper.WareSkuMapper;

import java.util.List;

import static org.xiaowu.behappy.thirdparty.enums.BizCode.INSUFFICIENT_INVENTORY;

/**
 *
 * @author xiaowu
 */
@Slf4j
@Service
@AllArgsConstructor
public class WareSkuService extends ServiceImpl<WareSkuMapper, WareSkuEntity> implements IService<WareSkuEntity> {

    private final WareOrderTaskDetailService wareOrderTaskDetailService;

    private final MqProperties mqProperties;

    private final RabbitTemplate rabbitTemplate;

    public void unlockStock(Long skuId, Integer num) {
        baseMapper.unlockStock(skuId, num);
    }

    public void orderLockStock(WareSkuLockDto wareSkuLockDto) {
        List<WareSkuLockItemDto> locks = wareSkuLockDto.getLocks();
        for (WareSkuLockItemDto lock : locks) {
            Long skuId = lock.getSkuId();
            //成功就返回 1 失败返回0
            Integer num = lock.getNum();
            Long update = baseMapper.lockSkuStock(skuId, num);
            if (update.equals(1)) {
                // 保存库存单
                WareOrderTaskDetailEntity wareOrderTaskDetailEntity = new WareOrderTaskDetailEntity();
                wareOrderTaskDetailEntity.setOrderSn(wareSkuLockDto.getOrderSn());
                wareOrderTaskDetailEntity.setSkuId(skuId);
                wareOrderTaskDetailEntity.setSkuName(lock.getSkuName());
                wareOrderTaskDetailEntity.setSkuNum(num);
                // 1-已锁定  2-已解锁  3-扣减
                wareOrderTaskDetailEntity.setLockStatus(1);
                wareOrderTaskDetailService.save(wareOrderTaskDetailEntity);
                // 发送mq消息,解锁库存
                rabbitTemplate.convertAndSend(mqProperties.getEventExchange(),
                        mqProperties.getStockReleaseRoutingKey(), wareOrderTaskDetailEntity);
            } else {
                // 当前商品已经没库存了
                throw new BeHappyException(INSUFFICIENT_INVENTORY.getCode(), INSUFFICIENT_INVENTORY.getMsg());
            }
        }
    }
}
