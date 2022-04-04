package org.xiaowu.behappy.basket.app.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.basket.dto.ModifyShopCartDto;
import org.xiaowu.behappy.basket.service.BasketService;
import org.xiaowu.behappy.basket.vo.ShopCartAmountVo;
import org.xiaowu.behappy.basket.vo.ShopCartItemVo;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.satoken.util.SaUtils;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xiaowu
 * 购物车接口
 */
@RestController
@RequestMapping("/app/v1/cart")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    /**
     * 获取购物车信息
     * @apiNote 获取购物车信息
     * @author xiaowu
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.basket.vo.ShopCartItemVo>>
     */
    @GetMapping
    public Response<List<ShopCartItemVo>> getCartOps() {
        Long userId = SaUtils.getUser().getUserId();
        List<ShopCartItemVo> cartOps = basketService.getCartOps(userId);
        return Response.ok(cartOps);
    }

    /**
     * 购物车数据变更
     * @apiNote 当count为正值时，增加商品数量，当count为负值时，将减去商品的数量，当最终count值小于0时，会将商品从购物车里面删除
     * @author xiaowu
     * @param modifyShopCartDto
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PutMapping
    public Response modifyCartItem(@Valid @RequestBody ModifyShopCartDto modifyShopCartDto) {
        Long userId = SaUtils.getUser().getUserId();
        basketService.modifyCartItem(modifyShopCartDto, userId);
        return Response.ok();
    }

    /**
     * 删除购物车item
     * @apiNote
     * @author xiaowu
     * @param basketIds
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @DeleteMapping
    public Response deleteCartItem(@RequestBody List<Long> basketIds) {
        Long userId = SaUtils.getUser().getUserId();
        basketService.deleteShopCartItemsByBasketIds(userId, basketIds);
        return Response.ok();
    }

    /**
     * 获取购物车所有商品数量
     * @apiNote 获取购物车所有商品数量
     * @author xiaowu
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Integer>
     */
    @GetMapping("/prod-count")
    public Response<Integer> prodCount() {
        Long userId = SaUtils.getUser().getUserId();
        Integer prodCount = basketService.prodCount(userId);
        return Response.ok(prodCount);
    }


    /**
     * 购物车金额总计
     * @apiNote 获取选中购物项金额总计
     * @author xiaowu
     * @param basketIds
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.basket.vo.ShopCartAmountVo>
     */
    @PostMapping("/amount")
    public Response<ShopCartAmountVo> prodTotalPay(@RequestBody List<Long> basketIds) {
        Long userId = SaUtils.getUser().getUserId();
        ShopCartAmountVo shopCartAmountVo = basketService.prodTotalPay(userId, basketIds);
        return Response.ok(shopCartAmountVo);
    }
}
