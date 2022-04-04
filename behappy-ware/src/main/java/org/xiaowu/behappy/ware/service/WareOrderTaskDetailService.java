package org.xiaowu.behappy.ware.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.core.util.ResponseConvert;
import org.xiaowu.behappy.order.enums.OrderStatus;
import org.xiaowu.behappy.order.feign.OrderFeign;
import org.xiaowu.behappy.ware.entity.WareOrderTaskDetailEntity;
import org.xiaowu.behappy.ware.mapper.WareOrderTaskDetailMapper;
import org.xiaowu.behappy.ware.to.WareOrderTaskDetailTo;

import java.util.List;

/**
 *
 * @author xiaowu
 */
@Slf4j
@Service
@AllArgsConstructor
public class WareOrderTaskDetailService extends ServiceImpl<WareOrderTaskDetailMapper, WareOrderTaskDetailEntity> implements IService<WareOrderTaskDetailEntity> {

    private final OrderFeign orderFeign;

    private final ResponseConvert responseConvert;

    private final WareSkuService wareSkuService;

    /**
     * 由库存服务进来, 判断是否需要释放库存
     * @param wareOrderTaskDetailTo
     */
    public void unlockStock(WareOrderTaskDetailTo wareOrderTaskDetailTo) {
        log.info("解锁库存: {}", wareOrderTaskDetailTo.toString());
        /**
         * 查询订单订单状态:
         *    已取消,解锁库存
         *    没取消 - 不能解锁
         */
        Response<Integer> orderStatusResponse = orderFeign.getOrderStatus(wareOrderTaskDetailTo.getOrderSn());
        Integer orderStatus = responseConvert.convert(orderStatusResponse, new TypeReference<Integer>() {
        });
        // 如果订单不存在或者已取消(未支付),则释放库存
        if (orderStatus == null || orderStatus.equals(OrderStatus.CLOSE.value())) {
            // 订单已取消且库存工作单状态1 才可以解锁
            if (wareOrderTaskDetailTo.getLockStatus() == 1) {
                unLock(wareOrderTaskDetailTo.getSkuId(), wareOrderTaskDetailTo.getSkuNum(), wareOrderTaskDetailTo.getId());
            }
        }
    }

    /**
     * 由订单服务进来(关闭订单)
     * 防止订单服务卡顿 导致订单状态一直改不了 库存消息有限到期 最后导致卡顿的订单 永远无法解锁库存
     */
    public void unlockStock(Long orderNum) {
        log.info("订单服务超时自动关闭: {}", orderNum);
        // 查询所有属于当前订单,且状态是1的
        LambdaQueryWrapper<WareOrderTaskDetailEntity> queryWrapper = Wrappers.<WareOrderTaskDetailEntity>lambdaQuery().
                eq(WareOrderTaskDetailEntity::getLockStatus, 1).
                eq(WareOrderTaskDetailEntity::getOrderSn, orderNum);
        List<WareOrderTaskDetailEntity> wareOrderTaskDetailEntities = baseMapper.selectList(queryWrapper);
        // 更新库存,更新库存工作单的状态
        wareOrderTaskDetailEntities.forEach(wareOrderTaskDetailEntity -> {
            unLock(wareOrderTaskDetailEntity.getSkuId(), wareOrderTaskDetailEntity.getSkuNum(), wareOrderTaskDetailEntity.getId());
        });
    }

    private void unLock(Long skuId, Integer num, Long taskDeailId) {
        // 更新库存
        wareSkuService.unlockStock(skuId, num);
        // 更新库存工作单的状态
        WareOrderTaskDetailEntity detailEntity = new WareOrderTaskDetailEntity();
        detailEntity.setId(taskDeailId);
        detailEntity.setLockStatus(2);
        baseMapper.updateById(detailEntity);
    }

}
