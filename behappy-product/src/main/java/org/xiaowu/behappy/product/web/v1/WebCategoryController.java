package org.xiaowu.behappy.product.web.v1;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.dto.SaveOrUpdateCategoryDto;
import org.xiaowu.behappy.product.entity.CategoryEntity;
import org.xiaowu.behappy.product.service.CategoryService;
import org.xiaowu.behappy.product.vo.WebCategoryVo;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * webduan品类接口
 * @author xiaowu
 */
@RestController
@RequestMapping("/web/v1/category")
@AllArgsConstructor
public class WebCategoryController {

    private final CategoryService categoryService;

    /**
     * 初始化table品类数据/判断2级品类树
     * @apiNote
     * @author xiaowu
     * @param categoryName
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.CategoryVo>>
     */
    @GetMapping
    public Response<List<WebCategoryVo>> listCategory(@RequestParam(required = false) String categoryName, @RequestParam(required = false) Integer grade) {
        List<WebCategoryVo> categoryVos = categoryService.listCategory(categoryName, grade);
        return Response.ok(categoryVos);
    }

    /**
     * 删除
     * @apiNote
     * @author xiaowu
     * @param ids
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @DeleteMapping
    public Response<Boolean> delete(@RequestBody ArrayList<Long> ids) {
        categoryService.removeByIds(ids);
        return Response.ok(true);
    }

    /**
     * save or update
     * @apiNote
     * @author xiaowu
     * @param dto
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @PostMapping
    public Response<Boolean> saveOrUpdate(@Valid @RequestBody SaveOrUpdateCategoryDto dto) {
        CategoryEntity categoryEntity = BeanUtil.copyProperties(dto, CategoryEntity.class);
        categoryEntity.setRecTime(LocalDateTime.now());
        categoryService.saveOrUpdate(categoryEntity);
        return Response.ok();
    }
}
