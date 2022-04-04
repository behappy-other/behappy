package org.xiaowu.behappy.common.redis.constant;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

/**
 * @author 小五
 * 定义缓存策略，结合springcache
 */
public interface CacheConstant {
    /**
     * 商品品分类信息
     */
    String PRODUCT_CATEGORY_CACHE = "PRODUCT:CATEGORY:";
    Duration PRODUCT_CATEGORY_DURATION = Duration.of(1, ChronoUnit.HOURS);

    /**
     * 购物车
     * 1个月
     */
    String BASKET_CART_CACHE = "BASKET:CART:";
    Duration BASKET_CART_DURATION = Duration.of(30, ChronoUnit.DAYS);

    /**
     * 短信code
     */
    String SMS_CODE_CACHE = "AUTH:SMS_CODE:";
    Duration SMS_CODE_DURATION = Duration.of(30, ChronoUnit.MINUTES);

    /**
     * 用户信息缓存
     */
    String USER_DETAILS_CACHE = "UPMS:USER_DETAILS:";
    Duration USER_DETAILS_DURATION = Duration.of(30, ChronoUnit.DAYS);

    /**
     * 用户确认订单后的ttl
     * 30min
     */
    String CONFIRM_ORDER_CACHE = "CONFIRM:ORDER:TOKEN:";
    Duration CONFIRM_ORDER_DURATION = Duration.of(30, ChronoUnit.MINUTES);
}
