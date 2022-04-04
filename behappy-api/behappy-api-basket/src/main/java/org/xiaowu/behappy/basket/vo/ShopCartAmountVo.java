package org.xiaowu.behappy.basket.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 购物车金额总计
 * @author xiaowu
 */
@Data
public class ShopCartAmountVo {

    /**
     * 总计
     */
    private BigDecimal finalMoney;

}