package org.xiaowu.behappy.basket.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_c_basket")
public class BasketEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4336938846685402280L;
    /**
     * 主键
     */
    @TableId
    private Long basketId;

    /**
     * 产品ID
     */
    private Long prodId;

    /**
     * SkuID
     */
    private Long skuId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 购物车产品个数
     */
    private Integer basketCount;

    /**
     * 购物时间
     */
    private LocalDateTime basketDate;

}