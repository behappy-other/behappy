package org.xiaowu.behappy.system.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaowu
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum BizCode {
    ORIGINAL_PASSWORD_NOT_CORRECT(11001, "账户/密码错误"),
    USER_ALREADY_EXISTS(11002, "用户已存在"),
    USER_NOT_EXISTS(11003, "用户不存在");

    private final int code;

    private final String msg;
}
