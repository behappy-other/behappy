package org.xiaowu.behappy.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 这里只放公共的，业务异样枚举应该放在各自的模块来管理
 * <p>Title: BizCodeEnum</p>
 * Description：
 * 错误码和错误信息定义类
 * 1. 错误码定义规则为5为数字
 * 2. 前两位表示业务场景，最后三位表示错误码。例如：100001。10:通用 001:系统未知异常
 * 3. 维护错误码后需要维护错误描述，将他们定义为枚举形式
 * 错误码列表：
 * 10: 通用
 * 11: 商品
 * 12: 订单
 * 13: 购物车
 * 14: 商户
 * 15: 用户
 * 16: 系统
 *
 * @author xiaowu
 */
@Getter
@AllArgsConstructor
public enum CommonBizCode {
    UNKNOWN_EXCEPTION(10000, "未知错误!"),
    VALID_EXCEPTION(10001, "参数验证错误!"),
    TO_MANY_REQUEST(10003, "TO_MANY_REQUEST!"),
    JSON_SERIALIZATION_ERROR(10004, "Json serialization error!"),
    FLOW_EXCEPTION(10004, "限流!"),
    DEGRADE_EXCEPTION(10004, "服务降级!"),
    PARAM_FLOW_EXCEPTION(10004, "热点控制!"),
    SYSTEM_BLOCK_EXCEPTION(10004, "系统保护!"),
    AUTHORITY_EXCEPTION(10004, "授权保护!"),
    UNAUTHORIZED(10401, "未认证或者认证已过期!"),
    FORBIDDEN(10403, "Without authorization, access is prohibited！");


    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private String msg;
}
