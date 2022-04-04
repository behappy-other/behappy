package org.xiaowu.behappy.member.app.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.member.dto.UserRegisterDto;
import org.xiaowu.behappy.member.service.SmsService;
import org.xiaowu.behappy.member.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author xiaowu
 * 用户注册相关接口
 */
@RestController
@RequestMapping("/app/v1/user")
@AllArgsConstructor
public class UserRegisterController {

    private final UserService userService;

    private final SmsService smsService;

    /**
     * 用户注册
     * @param userRegisterDto
     * @return org.xiaowu.behappy.common.core.util.Response
     * @author xiaowu
     */
    @PostMapping("/register")
    public Response register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        userService.register(userRegisterDto);
        return Response.ok();
    }


    /**
     * 发送验证码
     * @param phone
     * @return org.xiaowu.behappy.common.core.util.Response
     * @author xiaowu
     */
    @PostMapping("/sms")
    public Response sms(@NotBlank(message = "手机号不能为空") @RequestParam("phone") String phone) {
        smsService.sendSms(phone);
        return Response.ok();
    }

}
