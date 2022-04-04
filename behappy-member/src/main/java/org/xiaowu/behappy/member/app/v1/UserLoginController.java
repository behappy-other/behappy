package org.xiaowu.behappy.member.app.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.satoken.util.StpUserUtil;
import org.xiaowu.behappy.member.dto.UserLoginDto;
import org.xiaowu.behappy.member.service.UserService;
import org.xiaowu.behappy.member.vo.UserLoginResultVo;

import javax.validation.Valid;

/**
 * @author xiaowu
 * 用户登录相关接口
 */
@RestController
@RequestMapping("/app/v1/user")
@AllArgsConstructor
public class UserLoginController {

    private final UserService userService;

    /**
     * 用户登录
     * @param userLoginDto
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.member.vo.UserLoginResultVo>
     * @author xiaowu
     */
    @PostMapping("/login")
    public Response<UserLoginResultVo> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        UserLoginResultVo userLoginResultVo = userService.loginUser(userLoginDto);
        return Response.ok(userLoginResultVo);
    }

    /**
     * 退出登录
     * @author xiaowu
     * @param userId
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PostMapping("/logout")
    public Response logout(@RequestParam("userId") Long userId) {
        StpUserUtil.logout(userId);
        return Response.ok();
    }
}
