package org.xiaowu.behappy.member.feign.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.member.service.UserAddressService;
import org.xiaowu.behappy.member.vo.UserAddressDetailVo;

/**
 *
 * @author xiaowu
 */
@RestController
@RequestMapping("/feign/v1/user-addr")
@AllArgsConstructor
public class FeignUserAddrController {

    private final UserAddressService userAddressService;

    /**
     * 按照userId和addressId查询配送地址信息
     * @apiNote
     * @author xiaowu
     * @param userId
     * @param addrId
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.member.vo.UserAddressDetailVo>
     */
    @GetMapping
    public Response<UserAddressDetailVo> getUserAddrByUserIdAndAddrId(@RequestParam("userId") Long userId,
                                                                      @RequestParam("addrId") Long addrId) {
        UserAddressDetailVo detailAddress = userAddressService.getDetailAddress(addrId, userId);
        return Response.ok(detailAddress);
    }
}
