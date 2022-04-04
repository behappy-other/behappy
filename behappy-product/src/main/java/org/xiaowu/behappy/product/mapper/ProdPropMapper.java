package org.xiaowu.behappy.product.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.xiaowu.behappy.product.entity.ProdPropEntity;
import org.xiaowu.behappy.product.vo.ProdPropVo;
import org.xiaowu.common.mybatis.base.PageAdapter;

import java.util.List;

/**
 * @author xiaowu
 * @date 13:02
 * @apiNote
 */
public interface ProdPropMapper extends BaseMapper<ProdPropEntity> {
    /**
     * expr
     * @apiNote
     * @author xiaowu
     * @param adapter
     * @param prodProp
     * @return java.util.List<org.xiaowu.behappy.product.vo.ProdPropVo>
     */
    List<ProdPropVo> listPropAndValue(@Param("adapter") PageAdapter adapter, @Param("prodProp") ProdPropEntity prodProp);

    /**
     * expr
     * @apiNote
     * @author xiaowu
     * @param prodProp
     * @return long
     */
    long countPropAndValue(@Param("prodProp") ProdPropEntity prodProp);

}
