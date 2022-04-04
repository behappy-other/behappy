package org.xiaowu.behappy.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.feign.factory.SkuFeignFallbackFactory;
import org.xiaowu.behappy.product.vo.ShopSkuVo;

import java.util.List;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.PRODUCT_SERVICE;
import static org.xiaowu.behappy.common.core.constant.ServiceConstants.PRODUCT_URL_PREFIX;


/**
 * @author 小五
 */
@FeignClient(value = PRODUCT_SERVICE, contextId = "SkuFeign",
        fallbackFactory = SkuFeignFallbackFactory.class)
public interface SkuFeign {
    /**
     * 获取购物车sku信息
     * @apiNote
     * @author xiaowu
     * @param skuIds
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.ShopSkuVo>>
     */
    @PostMapping(PRODUCT_URL_PREFIX + "/feign/v1/sku/shop-cart")
    Response<List<ShopSkuVo>> shopSkuVoList(@RequestBody List<Long> skuIds);
}
