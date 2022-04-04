package org.xiaowu.behappy.product.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * web分页vo
 * @author xiaowu
 */
@Data
public class ProductPageVo {
    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 原价
     */
    private BigDecimal oriPrice;


    /**
     * 现价
     */
    private BigDecimal price;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Integer status;

    /**
     * 商品图片
     */
    private String imgs;
    
    /**
     * 商品主图
     */
    private String pic;

}
