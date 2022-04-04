package org.xiaowu.behappy.common.core.enums;

/**
 * @author 小五
 * @date 2020-05-27 15:46
 */
public enum BRStyle {
    CART("CART", "购物车模块"),
    DISCOUNT("DISCOUNT", "优惠区模块"),
    MEMBER("MEMBER", "会员模块"),
    ORDER("ORDER", "订单模块"),
    REPAST("REPAST", "餐品模块"),
    TENANT("TENANT", "租户模块"),
    NOTIFIER("NOTIFIER", "通知模块");

    private final String code;
    private final String info;

    BRStyle(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public String getInfo() {
        return info;
    }
}