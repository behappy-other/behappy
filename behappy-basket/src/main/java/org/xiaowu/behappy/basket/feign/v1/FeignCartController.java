package org.xiaowu.behappy.basket.feign.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.basket.service.BasketService;
import org.xiaowu.behappy.basket.vo.ShopCartItemVo;
import org.xiaowu.behappy.common.core.util.Response;

import java.util.List;

/**
 * @author xiaowu
 * feign 购物车接口
 */
@RestController
@RequestMapping("/feign/v1/cart")
@AllArgsConstructor
public class FeignCartController {

    private final BasketService basketService;

    /**
     * 获取用户购物车信息
     * @apiNote
     * @author xiaowu
     * @param userId
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.basket.vo.ShopCartItemVo>>
     */
    @GetMapping
    public Response<List<ShopCartItemVo>> userCartItems(@RequestParam("userId") Long userId) {
        List<ShopCartItemVo> cartOps = basketService.getCartOps(userId);
        return Response.ok(cartOps);
    }

    /**
     * 删除购物车item
     * @apiNote
     * @author xiaowu
     * @param basketIds
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @DeleteMapping
    public Response deleteCartItem(@RequestBody List<Long> basketIds, @RequestParam("userId") Long userId) {
        basketService.deleteShopCartItemsByBasketIds(userId, basketIds);
        return Response.ok();
    }
}
