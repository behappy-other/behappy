package org.xiaowu.behappy.product.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.product.entity.ProdPropValueEntity;
import org.xiaowu.behappy.product.mapper.ProdPropValueMapper;
import org.xiaowu.behappy.product.vo.ProdPropValueVo;

import java.util.List;

/**
 *
 * @author xiaowu
 */
@Service
public class ProdPropValueService extends ServiceImpl<ProdPropValueMapper, ProdPropValueEntity> implements IService<ProdPropValueEntity> {

    public void insertPropValues(Long propId, List<ProdPropValueVo> prodPropValues) {
        List<ProdPropValueEntity> prodPropValueEntities = BeanUtil.copyToList(prodPropValues, ProdPropValueEntity.class, CopyOptions.create());
        baseMapper.insertPropValues(propId, prodPropValueEntities);
    }

    public void removeByPropId(Long propId) {
        lambdaUpdate().eq(ProdPropValueEntity::getPropId, propId).remove();
    }
}
