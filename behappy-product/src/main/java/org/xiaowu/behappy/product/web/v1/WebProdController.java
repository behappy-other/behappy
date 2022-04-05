package org.xiaowu.behappy.product.web.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.dto.ProdPageDto;
import org.xiaowu.behappy.product.dto.ProductUploadDto;
import org.xiaowu.behappy.product.entity.ProductEntity;
import org.xiaowu.behappy.product.entity.SkuEntity;
import org.xiaowu.behappy.product.service.ProdService;
import org.xiaowu.behappy.product.service.SkuService;
import org.xiaowu.behappy.product.vo.ProdInfoVo;
import org.xiaowu.behappy.product.vo.ProductPageVo;
import org.xiaowu.behappy.product.vo.SkuInfoVo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * web端商品接口
 * @author xiaowu
 */
@RestController
@RequestMapping("/web/v1/prod")
@AllArgsConstructor
public class WebProdController {

    private final ProdService productService;

    private final SkuService skuService;

    /**
     * 分页获取商品信息
     */
    @GetMapping("/page")
    public Response<Page<ProductPageVo>> page(ProdPageDto product, Page page) {
        Page voPage = productService.page(page,
                new LambdaQueryWrapper<ProductEntity>()
                        .like(StrUtil.isNotBlank(product.getProdName()), ProductEntity::getProdName, product.getProdName())
                        .eq(product.getStatus() != null, ProductEntity::getStatus, product.getStatus())
                        .orderByDesc(ProductEntity::getPutAwayTime));
        List<ProductPageVo> productPageVos = BeanUtil.copyToList(voPage.getRecords(), ProductPageVo.class, CopyOptions.create());
        voPage.setRecords(productPageVos);
        return Response.ok(voPage);
    }


    /**
     * 批量删除商品
     * @apiNote
     * @author xiaowu
     * @param prodIds
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @DeleteMapping
    public Response<Boolean> deleteBatch(ArrayList<Long> prodIds) {
        productService.deleteBatch(prodIds);
        return Response.ok(true);
    }

    /**
     * 获取信息
     * @apiNote
     * @author xiaowu
     * @param prodId
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.product.vo.ProdInfoVo>
     */
    @GetMapping("/info/{prodId}")
    public Response<ProdInfoVo> info(@PathVariable("prodId") Long prodId) {
        ProdInfoVo prodInfoVo = productService.getProductByProdId(prodId);
        List<SkuEntity> skuList = skuService.listById(prodId);
        List<SkuInfoVo> skuInfoVos = BeanUtil.copyToList(skuList, SkuInfoVo.class, CopyOptions.create());
        prodInfoVo.setSkuList(skuInfoVos);
        return Response.ok(prodInfoVo);
    }

    /**
     * 新增/修改
     * @apiNote 商品上架
     * @author xiaowu
     * @param productUploadDto
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @PostMapping
    public Response<Boolean> saveOrUpdate(@Valid @RequestBody ProductUploadDto productUploadDto) {
        productService.saveOrUpdate(productUploadDto);
        return Response.ok(true);
    }

}
