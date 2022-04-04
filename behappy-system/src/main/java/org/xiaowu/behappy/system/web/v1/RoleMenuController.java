package org.xiaowu.behappy.system.web.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.system.service.RoleMenuService;

import java.util.List;

/**
 * 角色管理
 * @author xiaowu
 */
@AllArgsConstructor
@RestController
@RequestMapping("/web/v1/role-menu")
public class RoleMenuController {

    private final RoleMenuService roleMenuService;

    /**
     * 绑定角色和菜单的关系
     * @apiNote
     * @author xiaowu
     * @param roleId
     * @param menuIds
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @PostMapping("/{roleId}")
    public Response<Boolean> roleMenu(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        roleMenuService.setRoleMenu(roleId, menuIds);
        return Response.ok(true);
    }

    /**
     * 根据roleid获取所有id值
     * @apiNote
     * @author xiaowu
     * @param roleId
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < java.lang.Long>>
     */
    @GetMapping("/{roleId}")
    public Response<List<Long>> getRoleMenu(@PathVariable Long roleId) {
        return Response.ok(roleMenuService.getRoleMenu(roleId));
    }
}
