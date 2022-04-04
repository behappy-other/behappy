package org.xiaowu.behappy.ware.listener;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.ware.service.WareOrderTaskDetailService;
import org.xiaowu.behappy.ware.to.WareOrderTaskDetailTo;

import java.io.IOException;

/**
 * 这里接收释放库存队列信息
 * @author xiaowu
 */
@Slf4j
@Component
@RabbitListener(queues = "${rmq.stock-locked-release-queue}")
@RequiredArgsConstructor
public class StockReleaseListener {

    private final WareOrderTaskDetailService wareOrderTaskDetailService;

    /**
     * 等待解锁库存(ttl时间内未下单)
     * @param wareOrderTaskDetailTo
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitHandler
    public void handleStockLockedRelease(WareOrderTaskDetailTo wareOrderTaskDetailTo, Message message, Channel channel) throws IOException {
        try {
            wareOrderTaskDetailService.unlockStock(wareOrderTaskDetailTo);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("StockReleaseListener - handleStockLockedRelease: {}", e.getMessage());
            // 重新放回队列
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }

    /**
     * 订单关闭后 发送的消息这里接收
     */
    @RabbitHandler
    public void handleOrderCloseRelease(Long orderNum, Message message, Channel channel) throws IOException {
        try {
            wareOrderTaskDetailService.unlockStock(orderNum);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("StockReleaseListener - handleOrderCloseRelease: {}", e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
