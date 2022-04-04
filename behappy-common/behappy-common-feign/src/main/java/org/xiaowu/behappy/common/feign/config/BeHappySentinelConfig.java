package org.xiaowu.behappy.common.feign.config;

import com.alibaba.cloud.sentinel.feign.SentinelFeignAutoConfiguration;
import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xiaowu.behappy.common.feign.handle.BeHappyUrlBlockHandler;

/**
 * @author xiaowu
 * @date 2022/2/3
 * @apiNote
 */
@Configuration
@AutoConfigureBefore(SentinelFeignAutoConfiguration.class)
public class BeHappySentinelConfig {

    @Bean
    @ConditionalOnMissingBean
    public BlockExceptionHandler blockExceptionHandler(ObjectMapper objectMapper) {
        return new BeHappyUrlBlockHandler(objectMapper);
    }
}
