package org.xiaowu.behappy.basket.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaowu
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum BizCode {
    FAILED_ADD_CART(13001, "添加购物车失败");

    private final int code;

    private final String msg;
}
