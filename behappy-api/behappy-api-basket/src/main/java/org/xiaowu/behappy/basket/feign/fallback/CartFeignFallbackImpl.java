package org.xiaowu.behappy.basket.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.basket.feign.CartFeign;
import org.xiaowu.behappy.basket.vo.ShopCartItemVo;
import org.xiaowu.behappy.common.core.util.Response;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class CartFeignFallbackImpl implements CartFeign {

    @Setter
    Throwable cause;

    @Override
    public Response<List<ShopCartItemVo>> userCartItems(Long userId) {
        log.error("CartFeignFallbackImpl - userCartItems: {}", userId);
        return Response.failed(cause);
    }

    @Override
    public Response deleteCartItem(List<Long> basketIds, Long userId) {
        log.error("CartFeignFallbackImpl - deleteCartItem: {}-{}", basketIds.toString(), userId);
        return Response.failed(cause);
    }
}