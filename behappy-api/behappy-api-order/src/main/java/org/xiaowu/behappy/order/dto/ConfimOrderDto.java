package org.xiaowu.behappy.order.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * 确认订单,提交参数
 * @author xiaowu
 */
@Data
public class ConfimOrderDto implements Serializable {

    private static final long serialVersionUID = -5888511321750257781L;
    /**
     * 购物车id
     */
    @NotEmpty(message = "basketIds不能为空")
    private List<Long> basketIds;

    /**
     * 立即购买时提交的商品项
     */
    private OrderItemDto orderItemDto;

    /**
     * 地址ID，当为0时查默认地址
     */
    @NotNull(message = "地址不能为空")
    private Long addrId;

}
