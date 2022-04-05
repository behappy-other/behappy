package org.xiaowu.behappy.product.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * sku上架dto
 * @author xiaowu
 */
@Data
public class SkuUploadDto {

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 库存(-1表示无穷)
     */
    private Integer stocks;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 图片
     */
    private String pic;

    /**
     * 销售属性组合字符串,格式是p1:v1;p2:v2
     */
    private String properties;


    /**
     * @hidden
     * 0 正常 1 已被删除
     */
    private Integer isDelete;
}
