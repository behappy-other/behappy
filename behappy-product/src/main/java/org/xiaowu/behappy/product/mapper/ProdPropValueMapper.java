package org.xiaowu.behappy.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.xiaowu.behappy.product.entity.ProdPropValueEntity;

import java.util.List;

/**
 * @author xiaowu
 * @date 13:02
 * @apiNote
 */
public interface ProdPropValueMapper extends BaseMapper<ProdPropValueEntity> {

    /**
     * expr
     * @apiNote
     * @author xiaowu
     * @param propId
     * @param prodPropValues
     * @return void
     */
    void insertPropValues(@Param("propId") Long propId,
                          @Param("prodPropValues") List<ProdPropValueEntity> prodPropValues);
}
