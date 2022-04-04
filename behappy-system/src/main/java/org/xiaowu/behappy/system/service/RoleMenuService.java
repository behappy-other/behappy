package org.xiaowu.behappy.system.service;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaowu.behappy.system.entity.MenuEntity;
import org.xiaowu.behappy.system.entity.RoleMenuEntity;
import org.xiaowu.behappy.system.mapper.RoleMenuMapper;

import java.util.List;

/**
 *
 * @author xiaowu
 */
@Service
@AllArgsConstructor
@Slf4j
public class RoleMenuService extends ServiceImpl<RoleMenuMapper, RoleMenuEntity> implements IService<RoleMenuEntity> {

    private final MenuService menuService;

    @Transactional(rollbackFor = Exception.class)
    public void setRoleMenu(Long roleId, List<Long> menuIds) {
        // 先删除当前角色id所有的绑定关系
        baseMapper.deleteByRoleId(roleId);

        // 再把前端传过来的菜单id数组绑定到当前的这个角色id上去
        List<Long> menuIdsCopy = CollUtil.newArrayList(menuIds);
        for (Long menuId : menuIds) {
            MenuEntity menu = menuService.getById(menuId);
            // 二级菜单 并且传过来的menuId数组里面没有它的父级id
            if (menu.getPid() != null && !menuIdsCopy.contains(menu.getPid())) {
                // 那么我们就得补上这个父级id
                RoleMenuEntity roleMenu = new RoleMenuEntity();
                roleMenu.setRoleId(roleId);
                roleMenu.setMenuId(menu.getPid());
                save(roleMenu);
                menuIdsCopy.add(menu.getPid());
            }
            RoleMenuEntity roleMenu = new RoleMenuEntity();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            save(roleMenu);
        }
    }

    public List<Long> getRoleMenu(Long roleId) {
        return baseMapper.selectByRoleId(roleId);
    }
}
