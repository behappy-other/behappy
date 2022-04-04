package org.xiaowu.behappy.product.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.core.util.TreeUtils;
import org.xiaowu.behappy.product.entity.CategoryEntity;
import org.xiaowu.behappy.product.mapper.CategoryMapper;
import org.xiaowu.behappy.product.vo.CategoryVo;
import org.xiaowu.behappy.product.vo.WebCategoryVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.xiaowu.behappy.common.redis.constant.CacheConstant.PRODUCT_CATEGORY_CACHE;

/**
 * @author xiaowu
 * @date 12:46
 * @apiNote
 */
@Service
public class CategoryService extends ServiceImpl<CategoryMapper, CategoryEntity> implements IService<CategoryEntity> {

    @Cacheable(value = PRODUCT_CATEGORY_CACHE, key = "#parentId", unless = "#result == null")
    public List<CategoryVo> listCategoryByParentId(Long parentId) {
        LambdaQueryWrapper<CategoryEntity> queryWrapper = Wrappers.<CategoryEntity>lambdaQuery().
                select(CategoryEntity::getCategoryId,
                        CategoryEntity::getCategoryName,
                        CategoryEntity::getSeq,
                        CategoryEntity::getStatus,
                        CategoryEntity::getPic).
                eq(CategoryEntity::getParentId, parentId).
                eq(CategoryEntity::getStatus, 1).
                orderByDesc(CategoryEntity::getSeq);
        List<CategoryEntity> categoryEntities = baseMapper.selectList(queryWrapper);
        List<CategoryVo> categoryVos = BeanUtil.copyToList(categoryEntities, CategoryVo.class, CopyOptions.create());
        return categoryVos;
    }

    public List<WebCategoryVo> listCategory(String categoryName, Integer grade) {
        LambdaQueryWrapper<CategoryEntity> queryWrapper = new LambdaQueryWrapper<CategoryEntity>().
                select(CategoryEntity::getCategoryId,
                        CategoryEntity::getParentId,
                        CategoryEntity::getCategoryName,
                        CategoryEntity::getPic,
                        CategoryEntity::getSeq,
                        CategoryEntity::getStatus,
                        CategoryEntity::getGrade).
                like(StrUtil.isNotBlank(categoryName), CategoryEntity::getCategoryName, categoryName).
                le(ObjectUtil.isNotNull(grade), CategoryEntity::getGrade, grade).
                orderByAsc(CategoryEntity::getSeq);
        List<CategoryEntity> list = list(queryWrapper);
        List<WebCategoryVo> categoryVos = BeanUtil.copyToList(list, WebCategoryVo.class, CopyOptions.create());
        Collection<WebCategoryVo> webCategoryVos = TreeUtils.toTree(categoryVos, "categoryId", "parentId", "children", WebCategoryVo.class);
        return new ArrayList<>(webCategoryVos);
    }
}
