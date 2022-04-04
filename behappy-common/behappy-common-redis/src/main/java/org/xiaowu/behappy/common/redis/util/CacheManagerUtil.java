package org.xiaowu.behappy.common.redis.util;

import cn.hutool.extra.spring.SpringUtil;
import lombok.experimental.UtilityClass;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * @author xiaowu
 */
@UtilityClass
public class CacheManagerUtil {

    public <T> T getCache(String cacheName, String key) {
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            return null;
        }
        Cache.ValueWrapper valueWrapper = cache.get(key);
        if (valueWrapper == null) {
            return null;
        }
        return (T) valueWrapper.get();
    }

    public void putCache(String cacheName, String key, Object value) {
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, value);
        }
    }

    public void evictCache(String cacheName, String key) {
        CacheManager cacheManager = SpringUtil.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.evict(key);
        }
    }
}