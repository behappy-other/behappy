package org.xiaowu.behappy.common.satoken.util;

import lombok.Data;

import java.util.List;

@Data
public class UserInfo {

    /**
     * 用户在自己系统的用户id
     */
    private Long userId;
    private List<String> permissions;
    private List<String> roles;
    private String username;

}