package org.xiaowu.behappy.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.member.entity.RegionEntity;
import org.xiaowu.behappy.member.mapper.RegionMapper;
import org.xiaowu.behappy.member.vo.RegionVo;

import java.util.List;

/**
 * @author xiaowu
 * @apiNote
 */
@Service
public class RegionService extends ServiceImpl<RegionMapper, RegionEntity> implements IService<RegionEntity> {

    public List<RegionVo> regionsByPid(Long pid) {
        LambdaQueryWrapper<RegionEntity> queryWrapper = Wrappers.<RegionEntity>lambdaQuery().
                eq(RegionEntity::getParentId, pid);
        List<RegionEntity> regionEntities = baseMapper.selectList(queryWrapper);
        List<RegionVo> regionVos = BeanUtil.copyToList(regionEntities, RegionVo.class, CopyOptions.create());
        return regionVos;
    }
}
