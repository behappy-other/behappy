package org.xiaowu.behappy.thirdparty.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.dto.SendSmsDto;
import org.xiaowu.behappy.thirdparty.feign.factory.SmsFeignFallbackFactory;

import javax.validation.Valid;

import static org.xiaowu.behappy.common.core.constant.ServiceConstants.THIRDPARTY_SERVICE;
import static org.xiaowu.behappy.common.core.constant.ServiceConstants.THIRDPARTY_URL_PREFIX;


/**
 * @author 小五
 */
@FeignClient(value = THIRDPARTY_SERVICE, contextId = "SmsFeign",
        fallbackFactory = SmsFeignFallbackFactory.class)
public interface SmsFeign {

    /**
     * 发送验证码
     * @param sendSmsDto
     * @return org.xiaowu.behappy.common.core.util.Response
     * @author xiaowu
     */
    @PostMapping(THIRDPARTY_URL_PREFIX + "/app/v1/sms")
    Response sendSms(@Valid @RequestBody SendSmsDto sendSmsDto);
}
