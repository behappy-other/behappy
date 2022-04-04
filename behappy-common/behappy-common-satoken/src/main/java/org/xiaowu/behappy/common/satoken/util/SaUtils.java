package org.xiaowu.behappy.common.satoken.util;

import cn.dev33.satoken.stp.StpUtil;

/**
 * @author xiaowu
 * @apiNote
 */
public class SaUtils {

    public static UserInfo getUser() {
        UserInfo model = StpUserUtil.getSession().getModel(StpUserUtil.TYPE, UserInfo.class);
        return model;
    }

    public static UserInfo getSysUser() {
        UserInfo model = StpUtil.getSession().getModel(StpUtil.TYPE, UserInfo.class);
        return model;
    }
}
