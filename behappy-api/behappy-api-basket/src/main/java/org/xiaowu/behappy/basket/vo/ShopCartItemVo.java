package org.xiaowu.behappy.basket.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.xiaowu.behappy.common.core.serializer.ImgJsonSerializer;

import java.math.BigDecimal;

/**
 * @author xiaowu
 * 获取购物车信息
 */
@Data
public class ShopCartItemVo {

    /**
     * 购物车ID
     */
    private Long basketId;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 商品个数
     */
    private Integer prodCount;

    /**
     * 产品图片路径
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    /**
     * sku价格
     */
    private BigDecimal price;

    /**
     * 商品品ID
     */
    private Long prodId;

    /**
     * skuId
     */
    private Long skuId;

    /**
     * 商品总金额
     */
    private BigDecimal productTotalAmount;

    /**
     * 卖点
     */
    private String brief;

    /**
     * 规格
     */
    private String properties;

}
