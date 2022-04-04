package org.xiaowu.behappy.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.xiaowu.behappy.common.core.serializer.ImgJsonSerializer;

import java.math.BigDecimal;

/**
 * 返回sku具体信息
 *
 * @author xiaowu
 * @date 10:48
 * @apiNote
 */
@Data
public class SkuInfoVo {

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
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;

    /**
     * 销售属性组合字符串,格式是p1:v1;p2:v2
     */
    private String properties;
}
