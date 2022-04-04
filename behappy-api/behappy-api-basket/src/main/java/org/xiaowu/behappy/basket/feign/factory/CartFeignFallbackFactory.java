package org.xiaowu.behappy.basket.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.basket.feign.CartFeign;
import org.xiaowu.behappy.basket.feign.fallback.CartFeignFallbackImpl;

/**
 * @author 小五
 */
@Component
public class CartFeignFallbackFactory implements FallbackFactory<CartFeign> {

    @Override
    public CartFeign create(Throwable cause) {
        CartFeignFallbackImpl cartFeignFallback = new CartFeignFallbackImpl();
        cartFeignFallback.setCause(cause);
        return cartFeignFallback;
    }
}