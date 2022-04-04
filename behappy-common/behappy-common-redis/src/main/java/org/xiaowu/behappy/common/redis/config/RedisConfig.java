package org.xiaowu.behappy.common.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.redisson.spring.starter.RedissonAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.util.HashMap;
import java.util.Map;

import static org.xiaowu.behappy.common.redis.constant.CacheConstant.*;

/**
 * @author 小五
 * todo 集群模式
 * keytool -genkey -alias jwt -keyalg RSA -keystore jwt.jks
 */
@Slf4j
@EnableCaching
@AutoConfigureBefore({RedissonAutoConfiguration.class})
public class RedisConfig {

    private final String BEHAPPY_PREFIX = "BEHAPPY";

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        // 日期序列化设置
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.registerModule((new SimpleModule()));
        //禁用注解支持，防止一些@ignore的字段被忽略
        objectMapper.configure(MapperFeature.USE_ANNOTATIONS, false);
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会抛出异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);
        return objectMapper;
    }

    @Bean
    public RedisSerializer<String> redisKeySerializer() {
        return RedisSerializer.string();
    }

    @Bean
    public RedisSerializer<Object> redisValueSerializer() {
        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(getObjectMapper());
        return genericJackson2JsonRedisSerializer;
    }

    /**
     * RedisTemplate配置
     * key 为String类型
     * value 为 Object 类型
     * 都使用Jackson2JsonRedisSerializer进行序列化
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<String> redisKeySerializer, RedisSerializer<Object> redisValueSerializer) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setDefaultSerializer(redisValueSerializer);
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setHashKeySerializer(redisKeySerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }


    /**
     * RedisTemplate配置
     * key 和 value 都为String类型
     * 都使用Jackson2JsonRedisSerializer进行序列化
     */
    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory, RedisSerializer<String> redisKeySerializer, RedisSerializer<Object> redisValueSerializer) {
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        redisTemplate.setDefaultSerializer(redisValueSerializer);
        redisTemplate.setKeySerializer(redisKeySerializer);
        redisTemplate.setHashKeySerializer(redisKeySerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    @Primary
    @Bean
    public CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory, RedisSerializer<String> redisKeySerializer, RedisSerializer<Object> redisValueSerializer) {
        log.info("Init Redis CacheManager");
        RedisCacheWriter redisCacheWriter = RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory);
        RedisCacheConfiguration defaultCacheConfig = RedisCacheConfiguration.defaultCacheConfig().computePrefixWith(
                cacheName -> BEHAPPY_PREFIX.concat("-").concat(cacheName)).serializeKeysWith(
                RedisSerializationContext.SerializationPair.fromSerializer(redisKeySerializer)).serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(redisValueSerializer));

        // 指定cache name策略
        Map<String, RedisCacheConfiguration> initialCacheConfigurations = new HashMap<>(16);
        initialCacheConfigurations.put(PRODUCT_CATEGORY_CACHE, defaultCacheConfig.entryTtl(PRODUCT_CATEGORY_DURATION));
        initialCacheConfigurations.put(SMS_CODE_CACHE, defaultCacheConfig.entryTtl(SMS_CODE_DURATION));
        initialCacheConfigurations.put(USER_DETAILS_CACHE, defaultCacheConfig.entryTtl(USER_DETAILS_DURATION));
        initialCacheConfigurations.put(BASKET_CART_CACHE, defaultCacheConfig.entryTtl(BASKET_CART_DURATION));
        initialCacheConfigurations.put(CONFIRM_ORDER_CACHE, defaultCacheConfig.entryTtl(CONFIRM_ORDER_DURATION));
        return new RedisCacheManager(redisCacheWriter, defaultCacheConfig, initialCacheConfigurations);
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return (target, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(":" + method.getName() + ":");
            for (Object obj : objects) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}
