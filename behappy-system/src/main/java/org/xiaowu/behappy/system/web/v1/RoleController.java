package org.xiaowu.behappy.system.web.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.system.dto.RoleDto;
import org.xiaowu.behappy.system.entity.RoleEntity;
import org.xiaowu.behappy.system.service.RoleService;
import org.xiaowu.behappy.system.vo.RoleVo;

import java.util.Arrays;
import java.util.List;

/**
 * 角色管理
 * @author xiaowu
 */
@AllArgsConstructor
@RestController
@RequestMapping("/web/v1/role")
public class RoleController {

    private final RoleService roleService;

    /**
     * 保存或更新角色
     * @apiNote
     * @author xiaowu
     * @param roleDto
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @PostMapping
    public Response<Boolean> saveOrUpdate(@RequestBody RoleDto roleDto) {
        RoleEntity userEntity = BeanUtil.copyProperties(roleDto, RoleEntity.class);
        return Response.ok(roleService.saveOrUpdate(userEntity));
    }


    /**
     * 分页获取
     * @apiNote
     * @author xiaowu
     * @param page
     * @param name
     * @return org.xiaowu.behappy.common.core.util.Response<com.baomidou.mybatisplus.extension.plugins.pagination.Page < org.xiaowu.behappy.system.vo.RoleVo>>
     */
    @GetMapping("/page")
    public Response<Page<RoleVo>> findPage(Page page, @RequestParam(defaultValue = "") String name) {
        Page<RoleVo> voPage = roleService.findPage(page, name);
        return Response.ok(voPage);
    }

    /**
     * 根据ids删除
     * @apiNote
     * @author xiaowu
     * @param id
     * @return org.xiaowu.behappy.common.core.util.Response<Boolean>
     */
    @DeleteMapping
    public Response<Boolean> del(@RequestBody Long[] id) {
        return Response.ok(roleService.removeByIds(Arrays.asList(id)));
    }

    /**
     * 查询全部角色信息
     * @apiNote
     * @author xiaowu
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.system.vo.RoleVo>>
     */
    @GetMapping("/list")
    public Response<List<RoleVo>> roleList() {
        List<RoleEntity> list = roleService.list();
        List<RoleVo> roleVos = BeanUtil.copyToList(list, RoleVo.class, CopyOptions.create());
        return Response.ok(roleVos);
    }

}
