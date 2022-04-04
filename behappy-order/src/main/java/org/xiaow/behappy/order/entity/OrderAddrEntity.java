package org.xiaow.behappy.order.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_o_order_addr")
@EqualsAndHashCode(callSuper = true)
public class OrderAddrEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 2900702459559538317L;

    /**
     * ID
     */
    @TableId
    private Long addrOrderId;

    /**
     * 地址ID
     */
    private Long addrId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 地址
     */
    private String addr;


    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 区域ID
     */
    private Long areaId;

    /**
     * 手机
     */
    private String mobile;

    @Version
    private int version;
}