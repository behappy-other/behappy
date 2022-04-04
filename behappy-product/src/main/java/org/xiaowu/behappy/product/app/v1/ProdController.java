package org.xiaowu.behappy.product.app.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.service.ProdService;
import org.xiaowu.behappy.product.vo.ProdInfoVo;
import org.xiaowu.behappy.product.vo.ProductVo;

/**
 * @author xiaowu
 * 商品接口
 */
@RestController
@RequestMapping("/app/v1/prod")
@AllArgsConstructor
public class ProdController {

    private final ProdService prodService;

    /**
     * 根据分类ID获取该分类下所有的商品列表信息
     * @param categoryId
     * @param page
     * @return org.xiaowu.behappy.common.core.util.Response<com.baomidou.mybatisplus.extension.plugins.pagination.Page < org.xiaowu.behappy.product.vo.ProductVo>>
     * @author xiaowu
     */
    @GetMapping("/page")
    public Response<Page<ProductVo>> prodList(@RequestParam(value = "categoryId") Long categoryId, Page page) {
        Page<ProductVo> voPage = prodService.pageByCategoryId(categoryId, page);
        return Response.ok(voPage);
    }


    /**
     * 加载具体分类列表(商品信息)
     * @param prodId
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.product.vo.ProdInfoVo>
     * @author xiaowu
     */
    @GetMapping("/prod-info")
    public Response<ProdInfoVo> info(@RequestParam("prodId") Long prodId) {
        ProdInfoVo prodInfoVo = prodService.getProductByProdId(prodId);
        return Response.ok(prodInfoVo);
    }

}
