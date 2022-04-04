package org.xiaowu.behappy.member.service;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.core.exception.BeHappyException;
import org.xiaowu.behappy.common.dto.SendSmsDto;
import org.xiaowu.behappy.thirdparty.feign.SmsFeign;

import java.util.concurrent.TimeUnit;

import static org.xiaowu.behappy.common.redis.constant.CacheConstant.SMS_CODE_CACHE;
import static org.xiaowu.behappy.member.enums.BizCode.SMS_CODE_EXCEPTION;

/**
 * @author xiaowu
 * @apiNote
 */
@Service
@AllArgsConstructor
public class SmsService {

    private final SmsFeign smsFeign;

    private final StringRedisTemplate stringRedisTemplate;

    public void sendSms(String phone) {
        // 1、接口防刷
        // 2、先在redis中寻找，看看有没有相同的key，并且两次发送验证码的时间有没有隔1分钟
        String cacheCode = stringRedisTemplate.opsForValue().get(SMS_CODE_CACHE + phone);
        if (StrUtil.isNotEmpty(cacheCode)) {
            long CuuTime = Long.parseLong(cacheCode.split("_")[1]);
            if (System.currentTimeMillis() - CuuTime < 60 * 1000) {
                throw new BeHappyException(SMS_CODE_EXCEPTION.getCode(), SMS_CODE_EXCEPTION.getMsg());
            }
        }
        String code = RandomUtil.randomNumbers(6);
        String redisCode = code + "_" + System.currentTimeMillis();
        // 缓存验证码
        stringRedisTemplate.opsForValue().set(SMS_CODE_CACHE + phone, redisCode, 10, TimeUnit.MINUTES);
        // 3、如果以上通过，则先向redis中存入code，在调用短信接口发送验证码
        SendSmsDto sendSmsDto = new SendSmsDto();
        sendSmsDto.setCode(code);
        sendSmsDto.setMobile(phone);
        smsFeign.sendSms(sendSmsDto);
    }
}
