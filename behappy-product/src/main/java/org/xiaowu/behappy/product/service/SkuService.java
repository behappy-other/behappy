package org.xiaowu.behappy.product.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.product.dto.SkuUploadDto;
import org.xiaowu.behappy.product.entity.SkuEntity;
import org.xiaowu.behappy.product.mapper.SkuMapper;
import org.xiaowu.behappy.product.vo.ShopSkuVo;

import java.util.Collections;
import java.util.List;

/**
 * @author xiaowu
 * @date 11:09
 * @apiNote
 */
@Service
public class SkuService extends ServiceImpl<SkuMapper, SkuEntity> implements IService<SkuEntity> {


    public List<SkuEntity> listById(long prodId) {
        // select * from bh_sku where prod_id = #{prodId} and is_delete = 0
        LambdaQueryWrapper<SkuEntity> queryWrapper = Wrappers.<SkuEntity>lambdaQuery().
                eq(SkuEntity::getProdId, prodId).
                eq(SkuEntity::getIsDelete, 0);
        List<SkuEntity> skuEntities = baseMapper.selectList(queryWrapper);
        return skuEntities;
    }


    public List<ShopSkuVo> shopSkuVoList(List<Long> skuIds) {
        if (CollUtil.isEmpty(skuIds)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<SkuEntity> queryWrapper = Wrappers.<SkuEntity>lambdaQuery().
                select(SkuEntity::getSkuId,
                        SkuEntity::getPic,
                        SkuEntity::getPrice,
                        SkuEntity::getOriPrice,
                        SkuEntity::getProperties,
                        SkuEntity::getProdName,
                        SkuEntity::getSkuName,
                        SkuEntity::getStatus).
                eq(SkuEntity::getStatus, 1).
                in(SkuEntity::getSkuId, skuIds);
        List<SkuEntity> skuEntities = baseMapper.selectList(queryWrapper);
        List<ShopSkuVo> shopSkuVos = BeanUtil.copyToList(skuEntities, ShopSkuVo.class, CopyOptions.create());
        return shopSkuVos;
    }

    public void removeSkuBySkuId(List<Long> prodIds) {
        LambdaQueryWrapper<SkuEntity> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(SkuEntity::getProdId, prodIds);
        remove(queryWrapper);
    }

    public void insertBatch(Long prodId, List<SkuUploadDto> skuList) {
        List<SkuEntity> skuEntities = BeanUtil.copyToList(skuList, SkuEntity.class, CopyOptions.create());
        this.baseMapper.insertBatch(prodId, skuEntities);
    }
}
