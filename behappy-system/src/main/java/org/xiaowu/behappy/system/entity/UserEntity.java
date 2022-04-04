package org.xiaowu.behappy.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * User对象
 * @author xiaowu
 */
@Data
@TableName("bh_sys_user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -3600875380047545390L;

    @TableId
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 电话
     */
    private String phone;

    /**
     * 地址
     */
    private String address;

    /**
     * 头像
     */
    private String avatarUrl;

    /**
     * 角色
     */
    private String role;
}
