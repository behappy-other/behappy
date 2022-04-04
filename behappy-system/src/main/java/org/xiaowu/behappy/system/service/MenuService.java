package org.xiaowu.behappy.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.system.entity.MenuEntity;
import org.xiaowu.behappy.system.mapper.MenuMapper;
import org.xiaowu.behappy.system.vo.MenuVo;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiaowu
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, MenuEntity> implements IService<MenuEntity> {

    /**
     * 筛选1级和2级菜单树
     * @param name
     * @return
     */
    public List<MenuVo> findMenus(String name) {
        List<MenuVo> menuVoList = findByName(name);
        // 找出pid为null的一级菜单
        List<MenuVo> parentNodes = menuVoList.stream().filter(menu -> ObjectUtil.isNull(menu.getPid())).collect(Collectors.toList());
        // 找出一级菜单的子菜单
        for (MenuVo menu : parentNodes) {
            // 筛选所有数据中pid=父级id的数据就是二级菜单
            List<MenuVo> menuChildVos = menuVoList.stream().filter(m -> menu.getId().equals(m.getPid())).collect(Collectors.toList());
            menu.setChildren(menuChildVos);
        }
        return parentNodes;
    }

    public List<MenuVo> findByName(String name) {
        QueryWrapper<MenuEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        queryWrapper.orderByDesc("sort_num");
        List<MenuEntity> list = list(queryWrapper);
        List<MenuVo> menuVos = BeanUtil.copyToList(list, MenuVo.class, CopyOptions.create());
        return menuVos;
    }
}
