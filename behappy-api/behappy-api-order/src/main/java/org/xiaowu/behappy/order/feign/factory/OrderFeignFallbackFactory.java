package org.xiaowu.behappy.order.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.order.feign.OrderFeign;
import org.xiaowu.behappy.order.feign.fallback.OrderFeignFallbackImpl;

/**
 * @author 小五
 */
@Component
public class OrderFeignFallbackFactory implements FallbackFactory<OrderFeign> {

    @Override
    public OrderFeign create(Throwable cause) {
        OrderFeignFallbackImpl orderFeignFallback = new OrderFeignFallbackImpl();
        orderFeignFallback.setCause(cause);
        return orderFeignFallback;
    }
}