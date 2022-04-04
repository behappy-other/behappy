package org.xiaowu.behappy.order.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 直接购买,提交参数
 * @author xiaowu
 */
@Data
public class OrderItemDto implements Serializable {

    private static final long serialVersionUID = 6258105690313304650L;

    private Integer prodCount;

    private Long prodId;

    private Long skuId;
}
