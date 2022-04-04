package org.xiaowu.behappy.thirdparty.feign.fallback;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.dto.SendSmsDto;
import org.xiaowu.behappy.thirdparty.feign.SmsFeign;

@Slf4j
@Component
public class SmsFeignFallbackImpl implements SmsFeign {

    @Setter
    Throwable cause;

    @Override
    public Response sendSms(SendSmsDto sendSmsDto) {
        log.error("SmsFeignFallbackImpl - sendSms: {} - {}", cause.getLocalizedMessage(), sendSmsDto.toString());
        return Response.failed(cause);
    }
}