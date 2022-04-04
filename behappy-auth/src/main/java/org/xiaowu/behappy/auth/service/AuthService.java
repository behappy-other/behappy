package org.xiaowu.behappy.auth.service;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.context.model.SaRequest;
import cn.dev33.satoken.stp.StpUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.auth.enums.AuthType;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.satoken.util.StpUserUtil;

import static org.xiaowu.behappy.auth.constant.LoginConstant.*;

/**
 * @author xiaowu
 * @apiNote
 */
@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    public Response doLogin() {
        SaRequest req = SaHolder.getRequest();
        // 此处仅做模拟登录，真实环境应该查询数据进行登录
        String type = req.getParam(TYPE);
        String principal = req.getParam(PRINCIPAL);
        String credentials = req.getParam(CREDENTIALS);
        AuthType authType = AuthType.valueOf(type);
        log.info("AuthService", "登录类型: {}", type);
        if (authType.equals(AuthType.APP)) {
            StpUserUtil.login(principal);
            return Response.ok(StpUserUtil.getTokenValue());
        } else if (authType.equals(AuthType.WEB)) {
            StpUtil.login(principal);
            return Response.ok(StpUtil.getTokenValue());
        }
        return Response.failed();
    }

}