package org.xiaowu.behappy.basket.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaowu.behappy.basket.feign.factory.CartFeignFallbackFactory;
import org.xiaowu.behappy.basket.vo.ShopCartItemVo;
import org.xiaowu.behappy.common.core.util.Response;

import java.util.List;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.BASKET_SERVICE;
import static org.xiaowu.behappy.common.core.constant.ServiceConstants.BASKET_URL_PREFIX;


/**
 * @author 小五
 */
@FeignClient(value = BASKET_SERVICE, contextId = "CartFeign",
        fallbackFactory = CartFeignFallbackFactory.class)
public interface CartFeign {

    /**
     * 获取用户购物车信息
     * @apiNote
     * @author xiaowu
     * @param userId
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.basket.vo.ShopCartItemVo>>
     */
    @GetMapping(BASKET_URL_PREFIX + "/feign/v1/cart")
    Response<List<ShopCartItemVo>> userCartItems(@RequestParam("userId") Long userId);

    /**
     * 删除购物车item
     * @apiNote
     * @author xiaowu
     * @param basketIds
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @DeleteMapping(BASKET_URL_PREFIX + "/feign/v1/cart")
    Response deleteCartItem(@RequestBody List<Long> basketIds, @RequestParam("userId") Long userId);
}
