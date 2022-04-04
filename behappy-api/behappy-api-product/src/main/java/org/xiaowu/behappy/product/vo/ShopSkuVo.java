package org.xiaowu.behappy.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车加载sku信息
 * @author xiaowu
 */
@Data
public class ShopSkuVo {

    private Long skuId;

    private String pic;

    private BigDecimal price;

    private BigDecimal oriPrice;

    private String properties;

    private String prodName;

    private String skuName;

    private Integer status;
}
