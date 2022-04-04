package org.xiaowu.behappy.member.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.member.feign.UserAddrFeign;
import org.xiaowu.behappy.member.feign.fallback.UserAddrFeignFallbackImpl;

/**
 * @author 小五
 */
@Component
public class UserAddrFeignFallbackFactory implements FallbackFactory<UserAddrFeign> {

    @Override
    public UserAddrFeign create(Throwable cause) {
        UserAddrFeignFallbackImpl userAddrFeignFallback = new UserAddrFeignFallbackImpl();
        userAddrFeignFallback.setCause(cause);
        return userAddrFeignFallback;
    }
}