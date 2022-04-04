package org.xiaowu.behappy.thirdparty.app.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.dto.SendSmsDto;
import org.xiaowu.behappy.thirdparty.service.SmsService;

import javax.validation.Valid;

/**
 * @author xiaowu
 * 短信
 */
@RestController
@RequestMapping("/app/v1/sms")
@AllArgsConstructor
public class SmsController {

    private final SmsService smsService;

    /**
     * 发送验证码
     * @author xiaowu
     * @param sendSmsDto
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PostMapping
    public Response sendSms(@Valid @RequestBody SendSmsDto sendSmsDto) {
        smsService.sendSms(sendSmsDto);
        return Response.ok();
    }

}
