package org.xiaowu.behappy.product.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.feign.factory.ProdFeignFallbackFactory;
import org.xiaowu.behappy.product.vo.ShopProdVo;

import java.util.List;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.PRODUCT_SERVICE;
import static org.xiaowu.behappy.common.core.constant.ServiceConstants.PRODUCT_URL_PREFIX;


/**
 * @author 小五
 */
@FeignClient(value = PRODUCT_SERVICE, contextId = "ProdFeign",
        fallbackFactory = ProdFeignFallbackFactory.class)
public interface ProdFeign {
    /**
     * 获取购物车prod信息
     * @apiNote
     * @author xiaowu
     * @param prodIds
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.ShopProdVo>>
     */
    @PostMapping(PRODUCT_URL_PREFIX + "/feign/v1/prod/shop-cart")
    Response<List<ShopProdVo>> shopProdVoList(@RequestBody List<Long> prodIds);
}
