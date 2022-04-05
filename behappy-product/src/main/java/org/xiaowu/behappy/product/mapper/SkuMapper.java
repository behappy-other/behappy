package org.xiaowu.behappy.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.xiaowu.behappy.product.entity.SkuEntity;

import java.util.List;

/**
 * @author xiaowu
 * @date 11:11
 * @apiNote
 */
public interface SkuMapper extends BaseMapper<SkuEntity> {

    /**
     * 批量插入sku
     * @param prodId 商品id
     * @param skuList sku列表
     */
    void insertBatch(@Param("prodId") Long prodId, @Param("skuList") List<SkuEntity> skuList);
}
