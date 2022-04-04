package org.xiaowu.behappy.product.dto;

import lombok.Data;
import org.xiaowu.behappy.product.vo.ProdPropValueVo;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 商品属性
 * @author xiaowu
 */
@Data
public class ProdPropDto {
    /**
     * 更新则为空
     * 属性id
     */
    private Long propId;

    /**
     * 属性名称
     */
    @NotEmpty(message = "商品名称不能为空")
    private String propName;

    /**
     * @hidden 后端赋值
     * 1:销售属性(规格); 2:参数属性;
     */
    private Integer rule;

    /**
     * 属性值
     */
    private List<ProdPropValueVo> prodPropValues;
}
