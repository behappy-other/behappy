package org.xiaowu.behappy.order.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 提交订单
 * @author xiaowu
 */
@Data
public class SubmitOrderDto implements Serializable {
    private static final long serialVersionUID = -1240063606977840596L;

    /**
     * 备注信息
     */
    private String remarks;
}
