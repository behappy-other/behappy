package org.xiaowu.behappy.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.system.entity.RoleEntity;
import org.xiaowu.behappy.system.mapper.RoleMapper;
import org.xiaowu.behappy.system.vo.RoleVo;

import java.util.List;

/**
 * @author xiaowu
 */
@Service
@AllArgsConstructor
public class RoleService extends ServiceImpl<RoleMapper, RoleEntity> implements IService<RoleEntity> {


    public Page<RoleVo> findPage(Page page, String name) {
        QueryWrapper<RoleEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StrUtil.isNotBlank(name), "name", name);
        queryWrapper.orderByDesc("id");
        Page voPage = page(page, queryWrapper);
        List<RoleVo> roleVos = BeanUtil.copyToList(voPage.getRecords(), RoleVo.class, CopyOptions.create());
        voPage.setRecords(roleVos);
        return voPage;
    }

}
