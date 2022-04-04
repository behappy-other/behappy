package org.xiaowu.behappy.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.xiaowu.behappy.system.dto.UserPasswordDto;
import org.xiaowu.behappy.system.entity.UserEntity;

/**
 * @author xiaowu
 */
public interface UserMapper extends BaseMapper<UserEntity> {

    @Update("update bh_sys_user set password = #{newPassword} where username = #{username}")
    int updatePassword(UserPasswordDto userPasswordDTO);

    Page<UserEntity> findPage(Page<UserEntity> page, @Param("username") String username, @Param("email") String email, @Param("address") String address);
}
