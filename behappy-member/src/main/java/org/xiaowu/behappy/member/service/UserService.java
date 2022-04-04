package org.xiaowu.behappy.member.service;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.common.satoken.util.StpUserUtil;
import org.xiaowu.behappy.member.dto.UserLoginDto;
import org.xiaowu.behappy.member.dto.UserRegisterDto;
import org.xiaowu.behappy.member.entity.UserEntity;
import org.xiaowu.behappy.member.mapper.UserMapper;
import org.xiaowu.behappy.member.vo.UserLoginResultVo;

import java.time.LocalDateTime;

import static org.xiaowu.behappy.common.redis.constant.CacheConstant.SMS_CODE_CACHE;
import static org.xiaowu.behappy.member.enums.BizCode.*;

/**
 * @author xiaowu
 * @apiNote
 */
@Service
@AllArgsConstructor
public class UserService extends ServiceImpl<UserMapper, UserEntity> implements IService<UserEntity> {

    private final PasswordEncoder passwordEncoder;

    private final StringRedisTemplate stringRedisTemplate;

    private final SaTokenConfig saTokenConfig;

    public void register(UserRegisterDto userRegisterDto) {
        // 校验验证码
        String code = userRegisterDto.getCode();
        String redisCode = stringRedisTemplate.opsForValue().get(SMS_CODE_CACHE + userRegisterDto.getPrincipal());
        if (StrUtil.isNotBlank(redisCode) && code.equals(redisCode.split("_")[0])) {
            // 验证码通过,删除验证码
            stringRedisTemplate.delete(SMS_CODE_CACHE + userRegisterDto.getPrincipal());
            // 进行注册
            userRegisterDto.setCredentials(passwordEncoder.encode(userRegisterDto.getCredentials()));
            LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.<UserEntity>lambdaQuery().
                    eq(UserEntity::getPrincipal, userRegisterDto.getPrincipal());
            UserEntity userEntity = baseMapper.selectOne(queryWrapper);
            if (userEntity != null) {
                throw new BeHappyException(USER_EXISTS.getCode(), USER_EXISTS.getMsg());
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            UserEntity user = new UserEntity();
            user.setModifyTime(localDateTime);
            user.setUserRegtime(localDateTime);
            user.setStatus(1);
            user.setNickName(userRegisterDto.getPrincipal());
            user.setPrincipal(userRegisterDto.getPrincipal());
            user.setUserMobile(userRegisterDto.getPrincipal());
            user.setCredentials(userRegisterDto.getCredentials());
            baseMapper.insert(user);
            return;
        }
        // 验证不匹配
        throw new BeHappyException(SMS_CODE_NOT_MATCH.getCode(), SMS_CODE_NOT_MATCH.getMsg());
    }

    public UserLoginResultVo loginUser(UserLoginDto userLoginDto) {
        LambdaQueryWrapper<UserEntity> queryWrapper = Wrappers.<UserEntity>lambdaQuery().
                eq(UserEntity::getPrincipal, userLoginDto.getPrincipal());
        UserEntity userEntity = baseMapper.selectOne(queryWrapper);
        // 当前用户不存在
        if (ObjectUtil.isNull(userEntity)) {
            throw new BeHappyException(USER_NOT_EXISTS.getCode(), USER_NOT_EXISTS.getMsg());
        }
        // 密码错误
        if (!passwordEncoder.matches(userLoginDto.getCredentials(), userEntity.getCredentials())) {
            throw new BeHappyException(PASSWORD_WRONG.getCode(), PASSWORD_WRONG.getMsg());
        }
        // 账户被禁用
        if (userEntity.getStatus() != 1) {
            throw new BeHappyException(USER_NOT_ENABLED.getCode(), USER_NOT_ENABLED.getMsg());
        }
        // 登录
        StpUserUtil.login(userEntity.getUserId());

        UserLoginResultVo userLoginResultVo = new UserLoginResultVo();
        userLoginResultVo.setAccessToken(StpUserUtil.getTokenValue());
        userLoginResultVo.setTokenType(StpUserUtil.getTokenName());
        userLoginResultVo.setNickName(userEntity.getNickName());
        userLoginResultVo.setPic(userEntity.getPic());
        userLoginResultVo.setUserId(userEntity.getUserId());
        userLoginResultVo.setTokenPrefix(saTokenConfig.getTokenPrefix());
        return userLoginResultVo;
    }
}
