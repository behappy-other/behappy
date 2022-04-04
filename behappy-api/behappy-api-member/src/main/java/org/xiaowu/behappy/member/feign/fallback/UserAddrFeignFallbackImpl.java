package org.xiaowu.behappy.member.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.member.feign.UserAddrFeign;
import org.xiaowu.behappy.member.vo.UserAddressDetailVo;

/**
 * @author xiaowu
 */
@Slf4j
@Component
public class UserAddrFeignFallbackImpl implements UserAddrFeign {

    @Setter
    Throwable cause;

    @Override
    public Response<UserAddressDetailVo> getUserAddrByUserIdAndAddrId(Long userId, Long addrId) {
        log.error("UserAddrFeignFallbackImpl - getUserAddrByUserIdAndAddrId: {}-{}", userId, addrId);
        return Response.failed(cause);
    }
}