package org.xiaowu.behappy.product.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.product.feign.SkuFeign;
import org.xiaowu.behappy.product.feign.fallback.SkuFeignFallbackImpl;

/**
 * @author 小五
 */
@Component
public class SkuFeignFallbackFactory implements FallbackFactory<SkuFeign> {

    @Override
    public SkuFeign create(Throwable cause) {
        SkuFeignFallbackImpl skuFeignFallback = new SkuFeignFallbackImpl();
        skuFeignFallback.setCause(cause);
        return skuFeignFallback;
    }
}