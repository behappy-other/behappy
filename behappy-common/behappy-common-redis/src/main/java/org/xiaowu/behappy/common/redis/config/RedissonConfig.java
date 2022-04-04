package org.xiaowu.behappy.common.redis.config;

import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Bean;

/**
 * redisson配置
 *
 * @author xiaowu
 */
@AllArgsConstructor
@AutoConfigureBefore({RedissonAutoConfiguration.class})
public class RedissonConfig extends CachingConfigurerSupport {

    private static final String REDIS_PROTOCOL_PREFIX = "redis://";

    private static final String REDISS_PROTOCOL_PREFIX = "rediss://";

    private final RedisProperties redisProperties;

    private final RedissonProperties redissonProperties;

    @Bean(destroyMethod = "shutdown")
    @ConditionalOnMissingBean(RedissonClient.class)
    public RedissonClient redisson() {
        String prefix = RedissonConfig.REDIS_PROTOCOL_PREFIX;
        if (redisProperties.isSsl()) {
            prefix = RedissonConfig.REDISS_PROTOCOL_PREFIX;
        }
        Config config = new Config();
        config.setThreads(redissonProperties.getThreads())
                .setNettyThreads(redissonProperties.getNettyThreads())
                .setCodec(JsonJacksonCodec.INSTANCE)
                .setTransportMode(redissonProperties.getTransportMode());

        RedissonProperties.SingleServerConfig singleServerConfig = redissonProperties.getSingleServerConfig();
        // 使用单机模式
        config.useSingleServer()
                .setAddress(prefix + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setConnectTimeout(((Long) redisProperties.getTimeout().toMillis()).intValue())
                .setDatabase(redisProperties.getDatabase())
                .setPassword(StrUtil.isNotBlank(redisProperties.getPassword()) ? redisProperties.getPassword() : null)
                .setTimeout(singleServerConfig.getTimeout())
                .setRetryAttempts(singleServerConfig.getRetryAttempts())
                .setRetryInterval(singleServerConfig.getRetryInterval())
                .setSubscriptionsPerConnection(singleServerConfig.getSubscriptionsPerConnection())
                .setClientName(singleServerConfig.getClientName())
                .setIdleConnectionTimeout(singleServerConfig.getIdleConnectionTimeout())
                .setSubscriptionConnectionMinimumIdleSize(singleServerConfig.getSubscriptionConnectionMinimumIdleSize())
                .setSubscriptionConnectionPoolSize(singleServerConfig.getSubscriptionConnectionPoolSize())
                .setConnectionMinimumIdleSize(singleServerConfig.getConnectionMinimumIdleSize())
                .setConnectionPoolSize(singleServerConfig.getConnectionPoolSize())
                .setDnsMonitoringInterval(singleServerConfig.getDnsMonitoringInterval());

        return Redisson.create(config);
    }

}

