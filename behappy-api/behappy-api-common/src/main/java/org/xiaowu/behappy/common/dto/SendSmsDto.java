package org.xiaowu.behappy.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author xiaowu
 * 发送验证码参数
 */
@Data
public class SendSmsDto {

    /**
     * 手机号
     */
    @Pattern(regexp = "1[0-9]{10}", message = "请输入正确的手机号")
    private String mobile;

    /**
     * code
     */
    @NotBlank(message = "code不允许为空")
    private String code;
}
