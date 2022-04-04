package org.xiaow.behappy.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_o_order_item")
@EqualsAndHashCode(callSuper = true)
public class OrderItemEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 6165858601956042013L;

    /**
     * 订单项ID
     */
    @TableId
    private Long orderItemId;

    /**
     * 订单sub_number
     */
    private Long orderNumber;

    /**
     * 产品ID
     */
    private Long prodId;

    /**
     * 产品SkuID
     */

    private Long skuId;

    /**
     * 购物车产品个数
     */
    private Integer prodCount;

    /**
     * 产品名称
     */
    private String prodName;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 产品主图片路径
     */
    private String pic;

    /**
     * 产品价格
     */
    private BigDecimal price;

    /**
     * 用户Id
     */
    private Long userId;

    /**
     * 商品总金额
     */
    private BigDecimal productTotalAmount;

    /**
     * 购物时间
     */
    private LocalDateTime recTime;

    /**
     * 加入购物车的时间
     */
    private LocalDateTime basketDate;
}
