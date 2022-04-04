package org.xiaowu.behappy.ware.feign.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.ware.dto.WareSkuLockDto;
import org.xiaowu.behappy.ware.service.WareSkuService;

/**
 * 库存服务接口
 * @author xiaowu
 */
@RestController
@RequestMapping("/feign/v1/ware")
@AllArgsConstructor
public class FeignWareSkuController {

    private final WareSkuService wareSkuService;

    /**
     * 下单,锁定库存
     * @apiNote 下单, 锁定库存
     * @author xiaowu
     * @param wareSkuLockDto
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PostMapping("/lock/order")
    public Response orderLockStock(@RequestBody WareSkuLockDto wareSkuLockDto) {
        wareSkuService.orderLockStock(wareSkuLockDto);
        return Response.ok();
    }
}
