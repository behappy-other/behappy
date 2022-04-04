package org.xiaowu.behappy.common.satoken.listener;

import cn.dev33.satoken.listener.SaTokenListener;
import cn.dev33.satoken.stp.SaLoginModel;
import cn.dev33.satoken.stp.StpUtil;
import org.xiaowu.behappy.common.satoken.util.StpUserUtil;
import org.xiaowu.behappy.common.satoken.util.UserInfo;

/**
 * 自定义侦听器的实现
 *
 * @author xiaowu
 */
public class MySaTokenListener implements SaTokenListener {

    /**
     * 每次登录时触发
     */
    @Override
    public void doLogin(String loginType, Object loginId, SaLoginModel loginModel) {
        System.out.println(loginType);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(((Long) loginId));
        if (loginType.equals(StpUserUtil.TYPE)) {
            StpUserUtil.getSessionByLoginId(loginId).set(StpUserUtil.TYPE, userInfo);
        } else if (loginType.equals(StpUtil.TYPE)) {
            StpUtil.getSessionByLoginId(loginId).set(StpUtil.TYPE, userInfo);
        }
    }

    /**
     * 每次注销时触发
     */
    @Override
    public void doLogout(String loginType, Object loginId, String tokenValue) {
        // ...
    }

    /**
     * 每次被踢下线时触发
     */
    @Override
    public void doKickout(String loginType, Object loginId, String tokenValue) {
        // ...
    }

    /**
     * 每次被顶下线时触发
     */
    @Override
    public void doReplaced(String loginType, Object loginId, String tokenValue) {
        // ...
    }

    /**
     * 每次被封禁时触发
     */
    @Override
    public void doDisable(String loginType, Object loginId, long disableTime) {
        // ...
    }

    /**
     * 每次被解封时触发
     */
    @Override
    public void doUntieDisable(String loginType, Object loginId) {
        // ...
    }

    /**
     * 每次创建Session时触发
     */
    @Override
    public void doCreateSession(String id) {
        // ...
    }

    /**
     * 每次注销Session时触发
     */
    @Override
    public void doLogoutSession(String id) {
        // ...
    }

}
