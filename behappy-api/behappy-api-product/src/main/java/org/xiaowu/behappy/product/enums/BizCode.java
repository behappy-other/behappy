package org.xiaowu.behappy.product.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaowu
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum BizCode {
    GOODS_FROM_THE_SHELVES(11001, "商品已下架"),
    SAME_NAME_SPECIFICATION(11002, "已有相同名称规格");

    private final int code;

    private final String msg;
}
