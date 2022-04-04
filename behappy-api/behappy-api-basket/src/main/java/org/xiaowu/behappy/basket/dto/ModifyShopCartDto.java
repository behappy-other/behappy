package org.xiaowu.behappy.basket.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 购物车数据变更dto
 * @author xiaowu
 */
@Data
public class ModifyShopCartDto implements Serializable {

    private static final long serialVersionUID = -3554139089606737197L;

    /**
     * 商品ID
     */
    @NotNull(message = "商品ID不能为空")
    private Long prodId;

    /**
     * skuId
     */
    @NotNull(message = "skuId不能为空")
    private Long skuId;

    /**
     * 商品个数
     */
    @NotNull(message = "商品个数不能为空")
    private Integer count;
}
