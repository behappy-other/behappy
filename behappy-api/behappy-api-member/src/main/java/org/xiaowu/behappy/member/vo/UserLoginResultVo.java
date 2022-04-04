package org.xiaowu.behappy.member.vo;

import lombok.Data;

/**
 * @author xiaowu
 * 用户登录返回
 */
@Data
public class UserLoginResultVo {

    private String accessToken;

    private String nickName;

    private String pic;

    private String tokenType;

    private String tokenPrefix;

    private long userId;
}
