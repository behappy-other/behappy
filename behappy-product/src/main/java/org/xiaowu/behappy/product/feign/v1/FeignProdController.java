package org.xiaowu.behappy.product.feign.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.service.ProdService;
import org.xiaowu.behappy.product.vo.ShopProdVo;

import java.util.List;

/**
 * @author xiaowu
 * FeignProdController
 */
@RestController
@RequestMapping("/feign/v1/prod")
@AllArgsConstructor
public class FeignProdController {

    private final ProdService prodService;

    /**
     * 获取购物车prod信息(启用状态的)
     * @apiNote
     * @author xiaowu
     * @param prodIds
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.ShopProdVo>>
     */
    @PostMapping("/shop-cart")
    public Response<List<ShopProdVo>> shopProdVoList(@RequestBody List<Long> prodIds) {
        List<ShopProdVo> shopProdVos = prodService.shopProdVoList(prodIds);
        return Response.ok(shopProdVos);
    }
}
