package org.xiaowu.behappy.ware.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.xiaowu.behappy.ware.entity.WareSkuEntity;

/**
 *
 * @author xiaowu
 */
public interface WareSkuMapper extends BaseMapper<WareSkuEntity> {

    /**
     * 添加库存
     * @param skuId
     * @param skuNum
     */
    void addStock(@Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    /**
     * 获取库存
     * @param id
     * @return
     */
    Long getSkuStock(@Param("id") Long id);

    /**
     * 锁定库存
     * @param skuId
     * @param num
     * @return
     */
    Long lockSkuStock(@Param("skuId") Long skuId, @Param("num") Integer num);

    /**
     * 释放库存
     * @param skuId
     * @param num
     */
    void unlockStock(@Param("skuId") Long skuId, @Param("num") Integer num);

}
