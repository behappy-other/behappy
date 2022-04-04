package org.xiaowu.behappy.common.core.constant;

/**
 * 权限相关常量定义
 * @author 小五
 */
public interface AuthConstant {

    String APP_CLIENT_ID = "behappy-app";

    String WEB_CLIENT_ID = "behappy-web";

    /**
     * 后台管理接口路径匹配
     */
    String WEB_URL_PATTERN = "/**/web/**";

    String APP_URL_PATTERN = "/**/app/**";

    /**
     * 认证信息Http请求头
     */
    String JWT_TOKEN_HEADER = "Authorization";

    /**
     * JWT令牌前缀
     */
    String JWT_TOKEN_PREFIX = "Bearer ";

    /**
     * 用户信息Http请求头
     */
    String USER_TOKEN_HEADER = "behappy-user";

}
