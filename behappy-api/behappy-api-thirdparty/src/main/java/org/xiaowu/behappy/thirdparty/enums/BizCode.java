package org.xiaowu.behappy.thirdparty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaowu
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum BizCode {
    SMS_SEND_FAIL(15001, "验证码发送失败");

    private final int code;

    private final String msg;
}
