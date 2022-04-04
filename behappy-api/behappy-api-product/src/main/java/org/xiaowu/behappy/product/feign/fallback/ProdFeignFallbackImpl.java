package org.xiaowu.behappy.product.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.feign.ProdFeign;
import org.xiaowu.behappy.product.vo.ShopProdVo;

import java.util.List;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class ProdFeignFallbackImpl implements ProdFeign {

    @Setter
    Throwable cause;

    @Override
    public Response<List<ShopProdVo>> shopProdVoList(List<Long> prodIds) {
        log.error("ProdFeignFallbackImpl - shopProdVoList: {}", prodIds.toString());
        return Response.failed(cause);
    }
}