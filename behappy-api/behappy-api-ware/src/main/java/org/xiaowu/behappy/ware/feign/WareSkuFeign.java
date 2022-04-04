package org.xiaowu.behappy.ware.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.ware.dto.WareSkuLockDto;
import org.xiaowu.behappy.ware.feign.factory.WareSkuFeignFallbackFactory;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.WARE_SERVICE;
import static org.xiaowu.behappy.common.core.constant.ServiceConstants.WARE_URL_PREFIX;


/**
 * @author 小五
 */
@FeignClient(value = WARE_SERVICE, contextId = "WareSkuFeign",
        fallbackFactory = WareSkuFeignFallbackFactory.class)
public interface WareSkuFeign {

    /**
     * 下单,锁定库存
     * @apiNote 下单, 锁定库存
     * @author xiaowu
     * @param wareSkuLockDto
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PostMapping(WARE_URL_PREFIX + "/feign/v1/ware/lock/order")
    Response orderLockStock(@RequestBody WareSkuLockDto wareSkuLockDto);
}
