package org.xiaowu.behappy.order.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.order.feign.OrderFeign;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class OrderFeignFallbackImpl implements OrderFeign {

    @Setter
    Throwable cause;

    @Override
    public Response<Integer> getOrderStatus(Long orderNum) {
        log.error("OrderFeignFallbackImpl - getOrderStatus: {}", orderNum);
        return Response.failed(cause);
    }
}