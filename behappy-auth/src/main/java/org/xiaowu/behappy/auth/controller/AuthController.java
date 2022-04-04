package org.xiaowu.behappy.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.auth.service.AuthService;
import org.xiaowu.behappy.common.core.util.Response;

/**
 * @author xiaowu
 */
@RestController
@AllArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 处理双端的登录请求
     */
    @RequestMapping("/login")
    public Response login() {
        return authService.doLogin();
    }

}
