package org.xiaowu.behappy.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xiaowu
 * 用户注册
 */
@Data
public class UserRegisterDto implements Serializable {
    private static final long serialVersionUID = -5417600748424189408L;

    /**
     * 账户/手机号:唯一
     */
    @NotNull(message = "账户不能为空")
    private String principal;

    /**
     * 密码
     */
    @NotNull(message = "用户密码不能为空")
    private String credentials;

    /**
     * code
     */
    @NotBlank(message = "验证码不能为空")
    private String code;
}
