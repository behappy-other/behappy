/*
 * Copyright (c) 2018-2999 广州市蓝海创新科技有限公司 All rights reserved.
 *
 * https://www.mall4j.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package org.xiaowu.behappy.product.vo;

import lombok.Data;

/**
 * 商品属性值
 * @author xiaowu
 */
@Data
public class ProdPropValueVo {
    /**
     * 属性值ID
     */
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