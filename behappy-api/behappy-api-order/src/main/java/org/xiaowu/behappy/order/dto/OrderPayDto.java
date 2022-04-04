package org.xiaowu.behappy.order.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 支付参数
 * @author xiaowu
 */
@Data
public class OrderPayDto {

    /**
     * 订单号
     */
    @NotBlank(message = "订单号不能为空")
    private String orderNumber;

    /**
     * 支付方式 (1:微信支付 2:支付宝)
     */
    @NotNull(message = "支付方式不能为空")
    private Integer payType;
}
