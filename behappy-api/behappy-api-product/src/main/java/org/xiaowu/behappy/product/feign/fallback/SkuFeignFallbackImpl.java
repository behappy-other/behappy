package org.xiaowu.behappy.product.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.feign.SkuFeign;
import org.xiaowu.behappy.product.vo.ShopSkuVo;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class SkuFeignFallbackImpl implements SkuFeign {

    @Setter
    Throwable cause;

    @Override
    public Response<List<ShopSkuVo>> shopSkuVoList(List<Long> skuIds) {
        log.error("SkuFeignFallbackImpl - shopSkuVoList: {}", skuIds.toString());
        return Response.failed(cause);
    }
}