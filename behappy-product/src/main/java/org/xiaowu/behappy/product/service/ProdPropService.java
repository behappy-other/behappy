package org.xiaowu.behappy.product.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.product.dto.ProdPropDto;
import org.xiaowu.behappy.product.entity.ProdPropEntity;
import org.xiaowu.behappy.product.enums.ProdPropRule;
import org.xiaowu.behappy.product.mapper.ProdPropMapper;
import org.xiaowu.behappy.product.vo.ProdPropVo;
import org.xiaowu.common.mybatis.base.PageAdapter;

import java.util.List;
import java.util.Objects;

import static org.xiaowu.behappy.product.enums.BizCode.SAME_NAME_SPECIFICATION;

/**
 *
 * @author xiaowu
 */
@Service
@RequiredArgsConstructor
public class ProdPropService extends ServiceImpl<ProdPropMapper, ProdPropEntity> implements IService<ProdPropEntity> {

    private final ProdPropValueService prodPropValueService;

    public Page<ProdPropVo> page(String propName, Page page) {
        ProdPropEntity prodPropEntity = new ProdPropEntity();
        prodPropEntity.setRule(ProdPropRule.SPEC.value());
        prodPropEntity.setPropName(propName);
        List<ProdPropVo> prodPropVos = baseMapper.listPropAndValue(new PageAdapter(page), prodPropEntity);
        long total = baseMapper.countPropAndValue(prodPropEntity);
        Page<ProdPropVo> voPage = new Page<>();
        voPage.setRecords(prodPropVos);
        voPage.setTotal(total);
        return voPage;
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveOrUpdateProp(ProdPropDto prodProp) {
        prodProp.setRule(ProdPropRule.SPEC.value());
        ProdPropEntity dbProdProp = lambdaQuery().
                eq(ProdPropEntity::getPropName, prodProp.getPropName()).
                eq(ProdPropEntity::getRule, prodProp.getRule()).one();
        if (dbProdProp != null && !Objects.equals(prodProp.getPropId(), dbProdProp.getPropId())) {
            throw new BeHappyException(SAME_NAME_SPECIFICATION.getCode(), SAME_NAME_SPECIFICATION.getMsg());
        }
        ProdPropEntity prodPropEntity = BeanUtil.copyProperties(prodProp, ProdPropEntity.class);
        if (ObjectUtil.isNull(prodPropEntity.getPropId())) {
            // 新增
            save(prodPropEntity);
        } else {
            // 修改
            updateById(prodPropEntity);
        }
        if (CollUtil.isEmpty(prodProp.getProdPropValues())) {
            return;
        }
        // 先删除原有的属性值，再添加新的属性值
        prodPropValueService.removeByPropId(prodPropEntity.getPropId());
        prodPropValueService.insertPropValues(prodPropEntity.getPropId(), prodProp.getProdPropValues());
    }

    public void delete(List<Long> ids) {
        removeByIds(ids);
        for (Long prodId : ids) {
            // 先删除原有的属性值，再添加新的属性值
            prodPropValueService.removeByPropId(prodId);
        }
    }
}
