package org.xiaowu.behappy.thirdparty.feign.factory;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.thirdparty.feign.SmsFeign;
import org.xiaowu.behappy.thirdparty.feign.fallback.SmsFeignFallbackImpl;

/**
 * @author 小五
 */
@Component
public class SmsFeignFallbackFactory implements FallbackFactory<SmsFeign> {

    @Override
    public SmsFeign create(Throwable cause) {
        SmsFeignFallbackImpl smsFeignFallback = new SmsFeignFallbackImpl();
        smsFeignFallback.setCause(cause);
        return smsFeignFallback;
    }
}