package org.xiaowu.behappy.product.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品属性
 * @author xiaowu
 */
@Data
public class ProdPropVo {
    /**
     * 属性id
     */
    private Long propId;

    /**
     * 属性名称
     */
    private String propName;

    /**
     * 1:销售属性(规格); 2:参数属性;
     */
    private Integer rule;

    /**
     * 属性值
     */
    private List<ProdPropValueVo> prodPropValues;
}
