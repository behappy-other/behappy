package org.xiaow.behappy.order.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaow.behappy.order.service.OrderService;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.satoken.util.SaUtils;
import org.xiaowu.behappy.order.dto.ConfimOrderDto;
import org.xiaowu.behappy.order.dto.SubmitOrderDto;
import org.xiaowu.behappy.order.vo.ConfirmOrderVo;

import javax.validation.Valid;

/**
 * 订单服务接口
 * @author xiaowu
 */
@RestController
@RequestMapping("/app/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * 结算，生成订单信息
     * @apiNote 结算，生成订单信息
     * @author xiaowu
     * @param confimOrderDto
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.order.vo.ConfirmOrderVo>
     */
    @PostMapping("/confirm")
    public Response<ConfirmOrderVo> confirmOrder(@RequestBody @Valid ConfimOrderDto confimOrderDto) {
        Long userId = SaUtils.getUser().getUserId();
        ConfirmOrderVo confirmOrderVo = orderService.confirmOrder(confimOrderDto, userId);
        return Response.ok(confirmOrderVo);
    }

    /**
     * 提交订单
     * @apiNote 提交订单
     * @author xiaowu
     * @param submitOrderDto
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.String>
     */
    @PostMapping("/submit")
    public Response<Long> submitOrder(@RequestBody SubmitOrderDto submitOrderDto) {
        Long userId = SaUtils.getUser().getUserId();
        Long orderNumber = orderService.submitOrder(submitOrderDto, userId);
        return Response.ok(orderNumber);
    }
}
