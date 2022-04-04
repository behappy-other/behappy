package org.xiaowu.behappy.system.vo;

import lombok.Data;

import java.util.List;

/**
 * User对象
 * @author xiaowu
 */
@Data
public class UserVo {
    private Long id;

    /**
     * 用户名
     */
    private String username;

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
     * 当前用户拥有的菜单
     */
    private List<MenuVo> menus;

    /**
     * 角色
     */
    private String role;

    /**
     * 登录返回token
     */
    private String token;

    /**
     * 登录返回tokenType
     */
    private String tokenType;

    /**
     * 登录返回tokenPrefix
     */
    private String tokenPrefix;
}
