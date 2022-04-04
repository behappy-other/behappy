package org.xiaowu.behappy.ware.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.ware.dto.WareSkuLockDto;
import org.xiaowu.behappy.ware.feign.WareSkuFeign;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class WareSkuFeignFallbackImpl implements WareSkuFeign {

    @Setter
    Throwable cause;

    @Override
    public Response orderLockStock(WareSkuLockDto wareSkuLockDto) {
        log.error("SmsFeignFallbackImpl - orderLockStock: {}", wareSkuLockDto.toString());
        return Response.failed(cause);
    }
}