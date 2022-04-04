package org.xiaowu.behappy.order.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 订单信息,用于/confirm接口
 * @author xiaowu
 */
@Data
public class OrderItemVo {

    private String pic;

    private String primaryOrderNo;

    private String prodName;

    private String skuName;

    private BigDecimal price;

    private Integer prodCount;

    /**
     * 备注, 提交订单使用的参数
     * @hidden
     */
    private String remarks;

    /**
     * 商品品ID
     * @hidden 不需要前端展示
     */
    private Long prodId;

    /**
     * skuId
     * @hidden 不需要前端展示
     */
    private Long skuId;

    /**
     * 购物车ID
     * @hidden 不需要前端展示
     */
    private Long basketId;
}
