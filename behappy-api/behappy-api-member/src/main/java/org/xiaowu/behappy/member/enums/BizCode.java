package org.xiaowu.behappy.member.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaowu
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum BizCode {
    USER_EXISTS(15001, "当前用户已存在"),
    USER_NOT_EXISTS(15002, "当前用户不存在"),
    PASSWORD_WRONG(15003, "密码错误"),
    USER_NOT_ENABLED(15004, "账户被禁用"),
    SMS_CODE_NOT_MATCH(15005, "验证码匹配失败"),
    SMS_CODE_EXCEPTION(15006, "验证码发送失败"),
    USER_ADDRESS_NOT_EXISTS(15007, "当前用户地址不存在");

    private final int code;

    private final String msg;
}
