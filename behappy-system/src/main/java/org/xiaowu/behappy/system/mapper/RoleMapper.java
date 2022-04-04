package org.xiaowu.behappy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.xiaowu.behappy.system.entity.RoleEntity;

/**
 * @author xiaowu
 */
public interface RoleMapper extends BaseMapper<RoleEntity> {

    @Select("select id from bh_sys_role where flag = #{flag}")
    Long selectByFlag(@Param("flag") String flag);
}
