package org.xiaowu.behappy.product.web.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.dto.ProdPropDto;
import org.xiaowu.behappy.product.entity.ProdPropEntity;
import org.xiaowu.behappy.product.entity.ProdPropValueEntity;
import org.xiaowu.behappy.product.enums.ProdPropRule;
import org.xiaowu.behappy.product.service.ProdPropService;
import org.xiaowu.behappy.product.service.ProdPropValueService;
import org.xiaowu.behappy.product.vo.ProdPropValueVo;
import org.xiaowu.behappy.product.vo.ProdPropVo;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 商品规格
 * @author xiaowu
 */
@RestController
@RequestMapping("/web/v1/spec")
@AllArgsConstructor
public class WebSpecController {

    private final ProdPropService prodPropService;

    private final ProdPropValueService prodPropValueService;

    /**
     * 分页获取规格信息
     * @apiNote
     * @author xiaowu
     * @param propName
     * @param page
     * @return org.xiaowu.behappy.common.core.util.Response<com.baomidou.mybatisplus.extension.plugins.pagination.Page < org.xiaowu.behappy.product.vo.ProdPropVo>>
     */
    @GetMapping("/page")
    public Response<Page<ProdPropVo>> page(String propName, Page page) {
        Page<ProdPropVo> voPage = prodPropService.page(propName, page);
        return Response.ok(voPage);
    }

    /**
     * 获取所有的规格
     * @apiNote
     * @author xiaowu
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.ProdPropVo>>
     */
    @GetMapping("/list")
    public Response<List<ProdPropVo>> list() {
        LambdaQueryWrapper<ProdPropEntity> queryWrapper = new LambdaQueryWrapper<ProdPropEntity>().
                eq(ProdPropEntity::getRule, ProdPropRule.SPEC.value());
        List<ProdPropEntity> list = prodPropService.list(queryWrapper);
        List<ProdPropVo> prodPropVos = BeanUtil.copyToList(list, ProdPropVo.class, CopyOptions.create());
        return Response.ok(prodPropVos);
    }

    /**
     * 根据规格id获取规格值
     * @apiNote
     * @author xiaowu
     * @param specId
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.ProdPropValueVo>>
     */
    @GetMapping("/listSpecValue/{specId}")
    public Response<List<ProdPropValueVo>> listSpecValue(@PathVariable("specId") Long specId) {
        LambdaQueryWrapper<ProdPropValueEntity> queryWrapper = new LambdaQueryWrapper<ProdPropValueEntity>().
                eq(ProdPropValueEntity::getPropId, specId);
        List<ProdPropValueEntity> list = prodPropValueService.list(queryWrapper);
        List<ProdPropValueVo> prodPropValueVos = BeanUtil.copyToList(list, ProdPropValueVo.class, CopyOptions.create());
        return Response.ok(prodPropValueVos);
    }

    /**
     * 保存/修改
     * @apiNote
     * @author xiaowu
     * @param prodProp
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @PostMapping
    public Response<Boolean> saveOrUpdate(@Valid @RequestBody ProdPropDto prodProp) {
        prodPropService.saveOrUpdateProp(prodProp);
        return Response.ok(true);
    }

    @DeleteMapping
    public Response<Boolean> delete(@RequestBody ArrayList<Long> ids) {
        prodPropService.delete(ids);
        return Response.ok(true);
    }


}
