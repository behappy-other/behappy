package org.xiaowu.behappy.product.app.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.service.CategoryService;
import org.xiaowu.behappy.product.vo.CategoryVo;

import java.util.List;

/**
 * @author xiaowu
 * 分类接口
 */
@RestController
@RequestMapping("/app/v1/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    /**
     * 获取所有的产品分类信息，顶级分类的parentId为0,默认为顶级分类
     * @param parentId
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.product.vo.CategoryVo>>
     * @author xiaowu
     */
    @GetMapping("/categories")
    public Response<List<CategoryVo>> categories(@RequestParam(value = "parentId", defaultValue = "0") Long parentId) {
        List<CategoryVo> categoryVos = categoryService.listCategoryByParentId(parentId);
        return Response.ok(categoryVos);
    }
}
