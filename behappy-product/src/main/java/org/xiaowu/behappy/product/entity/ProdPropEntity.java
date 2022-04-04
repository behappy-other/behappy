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
 * 商品属性
 * @author xiaowu
 */
@Data
@TableName("bh_p_prop")
@EqualsAndHashCode(callSuper = true)
public class ProdPropEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 7084522594649067705L;
    /**
     * 属性id
     */
    @TableId
    private Long propId;

    /**
     * 属性名称
     */
    private String propName;

    /**
     * 1:销售属性(规格); 2:参数属性;
     */
    private Integer rule;
}
