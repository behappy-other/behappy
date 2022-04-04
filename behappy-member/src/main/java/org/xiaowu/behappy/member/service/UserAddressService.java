package org.xiaowu.behappy.member.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.member.dto.AddressDto;
import org.xiaowu.behappy.member.entity.UserAddressEntity;
import org.xiaowu.behappy.member.mapper.UserAddressMapper;
import org.xiaowu.behappy.member.vo.UserAddressDetailVo;
import org.xiaowu.behappy.member.vo.UserAddressVo;

import java.util.List;
import java.util.Optional;

import static org.xiaowu.behappy.member.enums.BizCode.USER_ADDRESS_NOT_EXISTS;

/**
 * @author xiaowu
 * @apiNote
 */
@Service
public class UserAddressService extends ServiceImpl<UserAddressMapper, UserAddressEntity> implements IService<UserAddressEntity> {

    public List<UserAddressVo> listAddressesByUserId(Long userId) {
        LambdaQueryWrapper<UserAddressEntity> queryWrapper = Wrappers.<UserAddressEntity>lambdaQuery().
                eq(UserAddressEntity::getUserId, userId);
        List<UserAddressEntity> userAddressEntities = baseMapper.selectList(queryWrapper);
        List<UserAddressVo> userAddressVos = BeanUtil.copyToList(userAddressEntities, UserAddressVo.class, CopyOptions.create());
        return userAddressVos;
    }

    @Transactional(rollbackFor = Exception.class)
    public void setDefaultAddress(Long addressId, Long userId) {
        // 先将当前用户的默认地址置为0
        LambdaUpdateWrapper<UserAddressEntity> setCommon0 = Wrappers.<UserAddressEntity>lambdaUpdate().
                set(UserAddressEntity::getCommonAddr, 0).
                eq(UserAddressEntity::getUserId, userId);
        // 再更新common_addr = 1
        LambdaUpdateWrapper<UserAddressEntity> setCommon1 = Wrappers.<UserAddressEntity>lambdaUpdate().
                set(UserAddressEntity::getCommonAddr, 1).
                eq(UserAddressEntity::getUserId, userId).
                eq(UserAddressEntity::getAddrId, addressId);
        baseMapper.update(null, setCommon0);
        baseMapper.update(null, setCommon1);
    }

    @SneakyThrows
    public UserAddressDetailVo getDetailAddress(Long addressId, Long userId) {
        // 如果前端传的addressId=0,则查默认地址
        LambdaQueryWrapper<UserAddressEntity> queryWrapper = Wrappers.<UserAddressEntity>lambdaQuery().
                select(
                        UserAddressEntity::getAddrId,
                        UserAddressEntity::getUserId,
                        UserAddressEntity::getReceiver,
                        UserAddressEntity::getProvince,
                        UserAddressEntity::getCity,
                        UserAddressEntity::getArea,
                        UserAddressEntity::getProvinceId,
                        UserAddressEntity::getCityId,
                        UserAddressEntity::getAreaId,
                        UserAddressEntity::getAddr,
                        UserAddressEntity::getMobile).
                eq(UserAddressEntity::getUserId, userId).
                eq(addressId == 0, UserAddressEntity::getCommonAddr, 1L).
                eq(addressId != 0, UserAddressEntity::getAddrId, addressId);
        UserAddressEntity userAddressEntity = Optional.
                of(baseMapper.selectOne(queryWrapper)).
                orElseThrow(() -> new BeHappyException(USER_ADDRESS_NOT_EXISTS.getCode(), USER_ADDRESS_NOT_EXISTS.getMsg()));
        UserAddressDetailVo userAddressDetailVo = BeanUtil.copyProperties(userAddressEntity, UserAddressDetailVo.class);
        return userAddressDetailVo;
    }

    public void addAddress(AddressDto addressDto, Long userId) {
        LambdaQueryWrapper<UserAddressEntity> queryWrapper = Wrappers.<UserAddressEntity>lambdaQuery().
                eq(UserAddressEntity::getUserId, userId);
        Integer addressCount = baseMapper.selectCount(queryWrapper);
        UserAddressEntity userAddressEntity = BeanUtil.copyProperties(addressDto, UserAddressEntity.class);
        if (addressCount == 0) {
            // 说明这是第一条配送地址,设为默认
            userAddressEntity.setCommonAddr(1);
        } else {
            userAddressEntity.setCommonAddr(0);
        }
        userAddressEntity.setUserId(userId);
        baseMapper.insert(userAddressEntity);
    }

    public void updateAddress(AddressDto addressDto, Long userId) {
        UserAddressEntity userAddressEntity = BeanUtil.copyProperties(addressDto, UserAddressEntity.class);
        userAddressEntity.setUserId(userId);
        int update = baseMapper.updateById(userAddressEntity);
        if (update == 0) {
            throw new BeHappyException(USER_ADDRESS_NOT_EXISTS.getCode(), USER_ADDRESS_NOT_EXISTS.getMsg());
        }
    }

    public void delAddress(Long addressId, Long userId) {
        LambdaQueryWrapper<UserAddressEntity> queryWrapper = Wrappers.<UserAddressEntity>lambdaQuery().
                eq(UserAddressEntity::getAddrId, addressId).
                eq(UserAddressEntity::getUserId, userId);
        int delete = baseMapper.delete(queryWrapper);
        if (delete == 0) {
            throw new BeHappyException(USER_ADDRESS_NOT_EXISTS.getCode(), USER_ADDRESS_NOT_EXISTS.getMsg());
        }
    }
}
