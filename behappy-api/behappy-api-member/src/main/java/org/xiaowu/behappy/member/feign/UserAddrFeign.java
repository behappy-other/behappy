package org.xiaowu.behappy.member.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.member.feign.factory.UserAddrFeignFallbackFactory;
import org.xiaowu.behappy.member.vo.UserAddressDetailVo;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.MEMBER_SERVICE;
import static org.xiaowu.behappy.common.core.constant.ServiceConstants.MEMBER_URL_PREFIX;


/**
 * @author 小五
 */
@FeignClient(value = MEMBER_SERVICE, contextId = "UserAddrFeign",
        fallbackFactory = UserAddrFeignFallbackFactory.class)
public interface UserAddrFeign {

    /**
     * 按照userId和addressId查询配送地址信息
     * @apiNote
     * @author xiaowu
     * @param userId
     * @param addrId
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.member.vo.UserAddressDetailVo>
     */
    @GetMapping(MEMBER_URL_PREFIX + "/feign/v1/user-addr")
    Response<UserAddressDetailVo> getUserAddrByUserIdAndAddrId(@RequestParam("userId") Long userId,
                                                               @RequestParam("addrId") Long addrId);
}
