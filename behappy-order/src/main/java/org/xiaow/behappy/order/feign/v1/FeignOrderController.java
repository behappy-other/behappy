package org.xiaow.behappy.order.feign.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaow.behappy.order.service.OrderService;
import org.xiaowu.behappy.common.core.util.Response;

/**
 * feign order
 * @author xiaowu
 */
@RestController
@RequestMapping("/feign/v1/order")
@RequiredArgsConstructor
public class FeignOrderController {

    private final OrderService orderService;

    /**
     * 查询订单状态
     * @apiNote 查询订单状态
     * @author xiaowu
     * @param orderNum
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Integer>
     */
    @GetMapping("/order-status")
    public Response<Integer> getOrderStatus(@RequestParam("orderNum") Long orderNum) {
        return Response.ok(orderService.getOrderStatus(orderNum));
    }
}
