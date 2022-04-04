package org.xiaow.behappy.order.listener;

import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.xiaow.behappy.order.service.OrderService;

import java.io.IOException;

/**
 * 判断状态,等待是否关闭订单
 * @author xiaowu
 */
@Slf4j
@Component
@RabbitListener(queues = "${rmq.order-release-queue}")
@RequiredArgsConstructor
public class OrderCloseListener {

    private final OrderService orderService;

    @RabbitHandler
    public void listener(Long orderNum, Channel channel, Message message) throws IOException {
        try {
            orderService.closeOrder(orderNum);
            // 手动调用支付宝收单
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            log.error("OrderCloseListener - listener: {}", e.getMessage());
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), true);
        }
    }
}
