package org.xiaowu.behappy.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.system.entity.DictEntity;
import org.xiaowu.behappy.system.mapper.DictMapper;
import org.xiaowu.behappy.system.vo.DictVo;

import java.util.List;

/**
 *
 * @author xiaowu
 */
@Service
public class DictService extends ServiceImpl<DictMapper, DictEntity> implements IService<DictEntity> {

    public List<DictVo> dictVoResponse(String type) {
        QueryWrapper<DictEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<DictEntity> list = list(queryWrapper);
        List<DictVo> dictVos = BeanUtil.copyToList(list, DictVo.class, CopyOptions.create());
        return dictVos;
    }
}
