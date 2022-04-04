package org.xiaow.behappy.order.app.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaow.behappy.order.service.AliPayService;
import org.xiaow.behappy.order.service.OrderService;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.order.dto.OrderPayDto;
import org.xiaowu.behappy.order.dto.PayDto;

import javax.validation.Valid;

/**
 * 订单接口
 * @author xiaowu
 */
@RestController
@RequestMapping("/app/v1/order")
@AllArgsConstructor
public class PayController {

    private final OrderService orderService;

    private final AliPayService aliPayService;

    /**
     * 根据订单号进行支付
     * @apiNote 根据订单号进行支付
     * @author xiaowu
     * @param orderPayDto
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PostMapping("/pay")
    public Response pay(@Valid @RequestBody OrderPayDto orderPayDto) {
        // todo 暂时只有支付宝
        PayDto payDto = orderService.pay(orderPayDto);
        aliPayService.pay(payDto);
        return Response.ok();
    }
}
