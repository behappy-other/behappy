package org.xiaowu.behappy.product.feign.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.service.SkuService;
import org.xiaowu.behappy.product.vo.ShopSkuVo;

import java.util.List;

/**
 * @author xiaowu
 * FeignSkuController
 */
@RestController
@RequestMapping("/feign/v1/sku")
@AllArgsConstructor
public class FeignSkuController {

    private final SkuService skuService;

    /**
     * 获取购物车sku信息(启用状态的)
     * @apiNote
     * @author xiaowu
     * @param skuIds
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.ShopSkuVo>>
     */
    @PostMapping("/shop-cart")
    public Response<List<ShopSkuVo>> shopSkuVoList(@RequestBody List<Long> skuIds) {
        List<ShopSkuVo> shopSkuVos = skuService.shopSkuVoList(skuIds);
        return Response.ok(shopSkuVos);
    }
}
