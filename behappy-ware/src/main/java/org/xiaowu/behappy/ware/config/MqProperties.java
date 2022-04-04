package org.xiaowu.behappy.ware.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 库存服务所需要的队列信息
 * @author xiaowu
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rmq")
public class MqProperties {

    private String stockLockedReleaseQueue;

    private String eventExchange;

    private String stockReleaseRoutingKey;

    private String delayQueue;

    private String letterRoutingKey;

    private Long ttl;
}
