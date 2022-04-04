package org.xiaowu.behappy.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.order.feign.factory.OrderFeignFallbackFactory;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.ORDER_SERVICE;
import static org.xiaowu.behappy.common.core.constant.ServiceConstants.ORDER_URL_PREFIX;


/**
 * @author 小五
 */
@FeignClient(value = ORDER_SERVICE, contextId = "OrderFeign",
        fallbackFactory = OrderFeignFallbackFactory.class)
public interface OrderFeign {

    /**
     * 查询订单状态
     * @apiNote 查询订单状态
     * @author xiaowu
     * @param orderNum
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Integer>
     */
    @GetMapping(ORDER_URL_PREFIX + "/feign/v1/order/order-status")
    Response<Integer> getOrderStatus(@RequestParam("orderNum") Long orderNum);
}
