package org.xiaow.behappy.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.behappy.order.enums.OrderStatus;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_o_order")
@EqualsAndHashCode(callSuper = true)
public class OrderEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -6434421558288381692L;
    /**
     * 订单ID
     */
    @TableId
    private Long orderId;

    /**
     * 产品名称,多个产品将会以逗号隔开
     */
    private String prodName;

    /**
     * 订购用户ID
     */
    private Long userId;

    /**
     * 订购流水号
     */
    private Long orderNumber;

    /**
     * 总值
     */
    private BigDecimal total;

    /**
     * 支付方式 1 微信支付 2 支付宝
     */
    private Integer payType;

    /**
     * 订单备注
     */
    private String remarks;

    /**
     * @see OrderStatus
     */
    private Integer status;

    /**
     * 用户订单地址Id
     */
    private Long addrOrderId;

    /**
     * 订单商品总数
     */
    private Integer productNums;

    /**
     * 付款时间
     * 支付接口之后赋值
     */
    private LocalDateTime payTime;

    /**
     * 发货时间
     * 后台点击发货后赋值
     */
    private LocalDateTime dvyTime;

    /**
     * 完成时间
     * 确认订单后赋值
     */
    private LocalDateTime finallyTime;

    /**
     * 取消时间
     * 我的订单,取消订单之后赋值
     */
    private LocalDateTime cancelTime;

    @Version
    private int version;
}
