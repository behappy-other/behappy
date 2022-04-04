package org.xiaowu.behappy.common.satoken.config;

import cn.dev33.satoken.config.SaTokenConfig;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.xiaowu.behappy.common.satoken.util.StpUserUtil;

public class SaTokenConfigure implements WebMvcConfigurer {

    // 获取配置Bean (以代码的方式配置Sa-Token, 此配置会覆盖yml中的配置)
    @Bean
    @Primary
    public SaTokenConfig saTokenConfig() {
        SaTokenConfig config = new SaTokenConfig();
        config.setTokenName("behappy");             // token名称 (同时也是cookie名称)
        config.setTimeout(30 * 24 * 60 * 60);       // token有效期，单位s 默认30天
        config.setActivityTimeout(-1);              // token临时有效期 (指定时间内无操作就视为token过期) 单位: 秒
        config.setIsConcurrent(true);               // 是否允许同一账号并发登录 (为true时允许一起登录, 为false时新登录挤掉旧登录)
        config.setIsShare(true);                    // 在多人登录同一账号时，是否共用一个token (为true时所有登录共用一个token, 为false时每次登录新建一个token)
        config.setTokenStyle("uuid");               // token风格
        config.setIsLog(true);                     // 是否输出操作日志
        config.setTokenPrefix("Bearer");
        return config;
    }

    // 注册Sa-Token的拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册路由拦截器，自定义认证规则
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {

            /*----------------------------------behappy-basket----------------------------------*/
            // 登录认证 -- 拦截所有路由，并排除登录和注册 用于开放登录
            SaRouter.match("/app/**").
                    notMatch("/app/v1/user/login").
                    notMatch("/app/v1/user/register").
                    notMatch("/app/v1/user/sms").
                    check(r -> StpUserUtil.checkLogin());
            //// 角色认证 -- 拦截以 admin 开头的路由，必须具备 admin 角色或者 super-admin 角色才可以通过认证
            //SaRouter.match("/app/v1/admin/**", r -> StpUtil.checkRoleOr("admin", "super-admin"));
            //
            //// 权限认证 -- 不同模块认证不同权限
            //SaRouter.match("/user/**", r -> StpUtil.checkPermission("user"));
            //SaRouter.match("/admin/**", r -> StpUtil.checkPermission("admin"));
            //SaRouter.match("/goods/**", r -> StpUtil.checkPermission("goods"));
            //SaRouter.match("/orders/**", r -> StpUtil.checkPermission("orders"));
            //SaRouter.match("/notice/**", r -> StpUtil.checkPermission("notice"));
            //SaRouter.match("/comment/**", r -> StpUtil.checkPermission("comment"));

            // 甚至你可以随意的写一个打印语句
            SaRouter.match("/**", r -> System.out.println("----啦啦啦----"));

            // 连缀写法
            //SaRouter.match("/**").check(r -> System.out.println("----啦啦啦----"));

        })).addPathPatterns("/**");
    }
}
