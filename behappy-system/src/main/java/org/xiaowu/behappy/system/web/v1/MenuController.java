package org.xiaowu.behappy.system.web.v1;

import cn.hutool.core.bean.BeanUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.system.dto.MenuDto;
import org.xiaowu.behappy.system.entity.MenuEntity;
import org.xiaowu.behappy.system.service.MenuService;
import org.xiaowu.behappy.system.vo.MenuVo;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单管理
 * @author xiaowu
 */
@AllArgsConstructor
@RestController
@RequestMapping("/web/v1/menu")
public class MenuController {

    private final MenuService menuService;

    /**
     * 根据条件查询菜单
     * @apiNote
     * @author xiaowu
     * @param name
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.system.vo.MenuVo>>
     */
    @GetMapping
    public Response<List<MenuVo>> findMenus(@RequestParam(required = false) String name) {
        List<MenuVo> menuVos = menuService.findMenus(name);
        return Response.ok(menuVos);
    }

    /**
     * 新增/更新菜单
     * @apiNote
     * @author xiaowu
     * @param menuDto
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @PostMapping
    public Response<Boolean> saveOrUpdate(@RequestBody MenuDto menuDto) {
        MenuEntity menuEntity = BeanUtil.copyProperties(menuDto, MenuEntity.class);
        return Response.ok(menuService.saveOrUpdate(menuEntity));
    }

    /**
     * 批量删除菜单
     * @apiNote
     * @author xiaowu
     * @param id
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @DeleteMapping
    public Response<Boolean> del(@RequestBody Long[] id) {
        return Response.ok(menuService.removeByIds(Arrays.asList(id)));
    }

    /**
     * 获取所有菜单id
     * @apiNote
     * @author xiaowu
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < java.lang.Long>>
     */
    @GetMapping("/ids")
    public Response<List<Long>> findAllIds() {
        List<Long> ids = menuService.list().stream().map(MenuEntity::getId).collect(Collectors.toList());
        return Response.ok(ids);
    }

}
