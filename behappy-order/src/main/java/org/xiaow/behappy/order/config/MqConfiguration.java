package org.xiaow.behappy.order.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author xiaowu
 */
@Configuration
@RequiredArgsConstructor
public class MqConfiguration {

    private final MqProperties mqProperties;


    /**
     * 死信队列
     * String name, boolean durable, boolean exclusive, boolean autoDelete,
     * @Nullable Map<String, Object> arguments
     */
    @Bean
    public Queue orderDelayQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", mqProperties.getEventExchange());
        arguments.put("x-dead-letter-routing-key", mqProperties.getLettuceRoutingKey());
        arguments.put("x-message-ttl", mqProperties.getTtl());
        Queue queue = new Queue(mqProperties.getDelayQueue(), true, false, false, arguments);
        return queue;
    }

    /**
     * 用于释放订单的普通队列
     * @return
     */
    @Bean
    public Queue orderReleaseOrderQueue() {
        Queue queue = new Queue(mqProperties.getOrderReleaseQueue(), true, false, false);
        return queue;
    }

    /**
     * 主题交换机
     * @return
     */
    @Bean
    public Exchange orderEventExchange() {
        return new TopicExchange(mqProperties.getEventExchange(), true, false);
    }

    /**
     * 创建订单的binding,
     * 规定创建完订单后,会根据createOrder路由建发送到延迟队列
     * ttl时间一到,发送给下游 - orderReleaseQueue
     * 如果没下游接收,则为删除
     */
    @Bean
    public Binding orderCreateOrderBinding() {
        return new Binding(mqProperties.getDelayQueue(), Binding.DestinationType.QUEUE,
                mqProperties.getEventExchange(), mqProperties.getCreateOrderRoutingKey(), null);
    }

    /**
     * 对应上面的binding
     * 接收死信队列的消息后,再发送到OrderReleaseQueue进行释放订单
     * @return
     */
    @Bean
    public Binding orderReleaseOrderBinding() {
        return new Binding(mqProperties.getOrderReleaseQueue(), Binding.DestinationType.QUEUE,
                mqProperties.getEventExchange(), mqProperties.getLettuceRoutingKey(), null);
    }

    /**
     * 订单释放直接和库存释放进行绑定
     * 这里的getReleaseOtherQueue时ware服务中绑定的释放库存queue
     */
    @Bean
    public Binding orderReleaseOtherBinding() {
        return new Binding(mqProperties.getReleaseOtherQueue(), Binding.DestinationType.QUEUE,
                mqProperties.getEventExchange(), mqProperties.getReleaseOtherRoutingKey() + ".#", null);
    }
}
