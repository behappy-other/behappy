package org.xiaowu.behappy.ware.dto;

import lombok.Data;

/**
 * 锁定库存
 * @author xiaowu
 */
@Data
public class WareSkuLockItemDto {

    /**
     * 需要锁住的skuid
     */
    private Long skuId;

    /**
     * skuName
     */
    private String skuName;

    /**
     * 需要锁住的数量
     */
    private Integer num;
}