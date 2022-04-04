package org.xiaowu.behappy.ware.to;

import lombok.Data;

import java.io.Serializable;

/**
 * mq接收,用于释放库存
 * @author xiaowu
 */
@Data
public class WareOrderTaskDetailTo implements Serializable {

    private static final long serialVersionUID = 7914958070048312456L;
    /**
     * id
     */
    private Long id;

    /**
     * sku_id
     */
    private Long skuId;

    /**
     * order_sn
     */
    private Long orderSn;

    /**
     * sku_name
     */
    private String skuName;

    /**
     * 购买个数
     */
    private Integer skuNum;

    /**
     * 1-已锁定  2-已解锁  3-扣减
     */
    private Integer lockStatus;

}
