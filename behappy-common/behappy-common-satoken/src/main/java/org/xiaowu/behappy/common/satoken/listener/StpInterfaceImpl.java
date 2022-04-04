package org.xiaowu.behappy.common.satoken.listener;

import cn.dev33.satoken.stp.StpInterface;
import org.xiaowu.behappy.common.satoken.util.SaUtils;

import java.util.List;

/**
 * 自定义权限验证接口扩展
 * 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
 *
 * @author xiaowu
 */
public class StpInterfaceImpl implements StpInterface {

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return SaUtils.getSysUser().getPermissions();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return SaUtils.getSysUser().getRoles();
    }

}
