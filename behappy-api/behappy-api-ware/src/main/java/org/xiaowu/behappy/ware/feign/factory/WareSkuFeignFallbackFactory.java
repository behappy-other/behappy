package org.xiaowu.behappy.ware.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.ware.feign.WareSkuFeign;
import org.xiaowu.behappy.ware.feign.fallback.WareSkuFeignFallbackImpl;

/**
 * @author 小五
 */
@Component
public class WareSkuFeignFallbackFactory implements FallbackFactory<WareSkuFeign> {

    @Override
    public WareSkuFeign create(Throwable cause) {
        WareSkuFeignFallbackImpl wareSkuFeignFallback = new WareSkuFeignFallbackImpl();
        wareSkuFeignFallback.setCause(cause);
        return wareSkuFeignFallback;
    }
}