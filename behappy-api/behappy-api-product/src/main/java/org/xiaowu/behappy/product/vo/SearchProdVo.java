package org.xiaowu.behappy.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.xiaowu.behappy.common.core.serializer.ImgJsonSerializer;

import java.math.BigDecimal;

/**
 * 搜索商品数据
 *
 * @author xiaowu
 */
@Data
public class SearchProdVo {

    /**
     * 商品id
     */
    private Long prodId;

    /**
     * 商品照片
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    /**
     * 商品名字
     */
    private String prodName;

    /**
     * 商品价格
     */
    private BigDecimal price;

    /**
     * 简要描述
     */
    private String brief;
}
