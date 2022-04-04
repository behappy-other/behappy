package org.xiaow.behappy.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 订单服务所需要的队列信息
 * @author xiaowu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rmq")
public class MqProperties {

    private String orderReleaseQueue;

    private String eventExchange;

    private String lettuceRoutingKey;

    private String delayQueue;

    private String createOrderRoutingKey;

    private Long ttl;

    /**
     * 下面俩用于释放库存
     */
    private String releaseOtherQueue;

    private String releaseOtherRoutingKey;

}
