package org.xiaowu.behappy.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.xiaowu.behappy.common.core.serializer.ImgJsonSerializer;

import java.math.BigDecimal;

/**
 * 加载分类列表response
 * @author xiaowu
 */
@Data
public class ProductVo {
    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 简要描述,卖点等
     */
    private String brief;

    /**
     * 商品主图
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    /**
     * 商品分类
     */
    private Long categoryId;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Integer status;

}
