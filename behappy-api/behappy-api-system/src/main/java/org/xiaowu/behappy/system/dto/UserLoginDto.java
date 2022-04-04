package org.xiaowu.behappy.system.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;


/**
 * @author xiaowu
 * 登录请求的参数
 */
@Data
public class UserLoginDto implements Serializable {
    private static final long serialVersionUID = 5925496869425695322L;

    /**
     * 账户
     */
    @NotEmpty(message = "用户不能为空")
    private String username;

    /**
     * 密码
     */
    @NotEmpty(message = "密码不能为空")
    private String password;
}