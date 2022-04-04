package org.xiaowu.behappy.ware.dto;

import lombok.Data;

import java.util.List;

/**
 * @author xiaowu
 * 锁定库存
 */
@Data
public class WareSkuLockDto {

    /**
     * 订单号
     */
    private Long orderSn;

    /**
     * 要锁住的所有库存信息
     */
    private List<WareSkuLockItemDto> locks;

}
