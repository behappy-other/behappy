package org.xiaowu.behappy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.xiaowu.behappy.system.entity.RoleMenuEntity;

import java.util.List;

/**
 * @author xiaowu
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenuEntity> {

    @Delete("delete from bh_sys_role_menu where role_id = #{roleId}")
    int deleteByRoleId(@Param("roleId") Long roleId);

    @Select("select menu_id from bh_sys_role_menu where role_id = #{roleId}")
    List<Long> selectByRoleId(@Param("roleId") Long roleId);

}
