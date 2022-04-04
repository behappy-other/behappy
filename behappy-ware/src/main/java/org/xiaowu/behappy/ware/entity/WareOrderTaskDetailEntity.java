package org.xiaowu.behappy.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * 库存工作单
 * @author xiaowu
 */
@Data
@TableName("bh_w_ware_order_task_detail")
@EqualsAndHashCode(callSuper = true)
public class WareOrderTaskDetailEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 5113905462402763453L;
    /**
     * id
     */
    @TableId
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
     * 1-已锁定  2-已解锁
     */
    private Integer lockStatus;

}
