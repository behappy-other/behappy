/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package org.xiaowu.behappy.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * 商品属性值
 * @author xiaowu
 */
@Data
@TableName("bh_p_prop_value")
@EqualsAndHashCode(callSuper = true)
public class ProdPropValueEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4663424781227963184L;
    /**
     * 属性值ID
     */
    @TableId
    private Long valueId;

    /**
     * 属性值
     */
    private String propValue;

    /**
     * 属性ID
     */
    private Long propId;

}