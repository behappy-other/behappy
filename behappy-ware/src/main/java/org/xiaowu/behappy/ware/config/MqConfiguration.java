package org.xiaowu.behappy.ware.config;

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
     * 生明主题交换机
     * String name, boolean durable, boolean autoDelete, Map<String, Object> arguments
     */
    @Bean
    public Exchange stockEventExchange() {
        return new TopicExchange(mqProperties.getEventExchange(), true, false);
    }

    /**
     * 释放库存队列
     * String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments
     */
    @Bean
    public Queue stockReleaseQueue() {
        return new Queue(mqProperties.getStockLockedReleaseQueue(), true, false, false);
    }

    /**
     * 和
     * @return
     */
    @Bean
    public Queue stockDelayQueue() {
        Map<String, Object> args = new HashMap<>();
        // 信死了 交给 [stock-event-exchange] 交换机
        args.put("x-dead-letter-exchange", mqProperties.getEventExchange());
        // 死信路由
        args.put("x-dead-letter-routing-key", mqProperties.getLetterRoutingKey());
        args.put("x-message-ttl", mqProperties.getTtl());
        return new Queue(mqProperties.getDelayQueue(), true, false, false, args);
    }

    /**
     * 接收死信队列的binding
     * 释放库存
     * String destination, DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
     */
    @Bean
    public Binding stockLockedReleaseBinding() {
        return new Binding(mqProperties.getStockLockedReleaseQueue(), Binding.DestinationType.QUEUE, mqProperties.getEventExchange(),
                mqProperties.getLetterRoutingKey() + ".#", null);
    }

    /**
     * 释放库存
     * 当过了ttl时间,会发送到StockLockedReleaseQueue
     * String destination, DestinationType destinationType, String exchange, String routingKey, @Nullable Map<String, Object> arguments
     */
    @Bean
    public Binding stockLockedBinding() {
        return new Binding(mqProperties.getDelayQueue(), Binding.DestinationType.QUEUE, mqProperties.getEventExchange(),
                mqProperties.getStockReleaseRoutingKey(), null);
    }
}
