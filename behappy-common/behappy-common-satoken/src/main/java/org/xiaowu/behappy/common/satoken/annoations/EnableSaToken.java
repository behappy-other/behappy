package org.xiaowu.behappy.common.satoken.annoations;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.xiaowu.behappy.common.satoken.config.SaAloneRedisInject;
import org.xiaowu.behappy.common.satoken.config.SaTokenConfigure;
import org.xiaowu.behappy.common.satoken.exception.SaTokenExceptionHandler;
import org.xiaowu.behappy.common.satoken.listener.MySaTokenListener;
import org.xiaowu.behappy.common.satoken.listener.StpInterfaceImpl;

import java.lang.annotation.*;

/**
 * @author xiaowu
 * @apiNote
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({SaAloneRedisInject.class, SaTokenConfigure.class,
        MySaTokenListener.class, StpInterfaceImpl.class, SaTokenExceptionHandler.class})
@EnableAutoConfiguration
public @interface EnableSaToken {
}
