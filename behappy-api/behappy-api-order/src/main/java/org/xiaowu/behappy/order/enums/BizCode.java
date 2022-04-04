package org.xiaowu.behappy.order.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author xiaowu
 */
@SuppressWarnings("AlibabaEnumConstantsMustHaveComment")
@Getter
@AllArgsConstructor
public enum BizCode {
    GOODS_CAN_NOT_IDENTIFY(15001, "订单包含无法识别的商品"),
    NOT_CHOOSE_GOODS(15002, "没有选择商品"),
    ORDER_HAS_BEEN_OVERDUE(15003, "订单已过期，请重新下单");

    private final int code;

    private final String msg;
}
