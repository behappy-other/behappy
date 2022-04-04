package org.xiaowu.behappy.ware.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * 商品库存
 * @author xiaowu
 */
@Data
@TableName("bh_w_ware_sku")
@EqualsAndHashCode(callSuper = true)
public class WareSkuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8789520614182526611L;

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
     * 库存数
     */
    private Integer stock;

    /**
     * sku_name
     */
    private String skuName;

    /**
     * 锁定库存,已锁定数量,默认0
     */
    private Integer stockLocked = 0;

}
