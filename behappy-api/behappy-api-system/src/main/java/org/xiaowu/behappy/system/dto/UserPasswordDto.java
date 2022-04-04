package org.xiaowu.behappy.system.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 修改密码
 * @author xiaowu
 */
@Data
public class UserPasswordDto implements Serializable {
    private static final long serialVersionUID = -2052356890409009692L;

    /**
     * username
     */
    @NotEmpty
    private String username;

    /**
     * password
     */
    @NotEmpty
    private String password;

    /**
     * newPassword
     */
    @NotEmpty
    private String newPassword;
}