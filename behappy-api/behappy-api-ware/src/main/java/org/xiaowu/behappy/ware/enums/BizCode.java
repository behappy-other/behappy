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
    INSUFFICIENT_INVENTORY(15001, "库存不足");

    private final int code;

    private final String msg;
}
