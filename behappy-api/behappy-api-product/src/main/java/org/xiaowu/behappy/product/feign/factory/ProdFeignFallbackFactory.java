package org.xiaowu.behappy.product.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.product.feign.ProdFeign;
import org.xiaowu.behappy.product.feign.fallback.ProdFeignFallbackImpl;

/**
 * @author 小五
 */
@Component
public class ProdFeignFallbackFactory implements FallbackFactory<ProdFeign> {

    @Override
    public ProdFeign create(Throwable cause) {
        ProdFeignFallbackImpl prodFeignFallback = new ProdFeignFallbackImpl();
        prodFeignFallback.setCause(cause);
        return prodFeignFallback;
    }
}