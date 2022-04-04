package org.xiaowu.behappy.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.xiaowu.behappy.common.core.serializer.ImgJsonSerializer;

import java.math.BigDecimal;
import java.util.List;

/**
 * 查看商品具体信息
 *
 * @author xiaowu
 * @date 10:45
 * @apiNote
 */
@Data
public class ProdInfoVo {
    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 商品内容,详情
     */
    private String content;

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
     * 商品主图
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String imgs;

    /**
     * sku列表
     */
    private List<SkuInfoVo> skuList;
}
