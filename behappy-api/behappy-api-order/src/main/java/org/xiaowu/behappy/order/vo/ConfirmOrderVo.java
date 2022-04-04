package org.xiaowu.behappy.order.vo;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author xiaowu
 * 确认订单信息,用于/confirm接口
 */
@Data
public class ConfirmOrderVo {

    /**
     * 商品总值
     */
    private BigDecimal total;

    /**
     * totalCount
     */
    private Integer totalCount;

    /**
     * userAddr
     */
    private UserAddrVo userAddrVo;

    /**
     * 订单信息
     */
    private List<OrderItemVo> orderItemVos;
}
