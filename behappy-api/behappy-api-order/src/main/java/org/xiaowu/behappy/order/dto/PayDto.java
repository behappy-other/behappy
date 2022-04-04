package org.xiaowu.behappy.order.dto;

import lombok.Data;

/**
 * @author xiaowu
 */
@Data
public class PayDto {
    /**
     *  商户订单号 必填
     */
    private String outTradeNo;

    /**
     * 订单名称 必填
     */
    private String subject;

    /**
     * 付款金额 必填
     */
    private String totalAmount;

    /**
     * 商品描述 可空
     */
    private String body;
}
