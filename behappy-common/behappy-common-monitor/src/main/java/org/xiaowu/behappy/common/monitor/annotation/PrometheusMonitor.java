package org.xiaowu.behappy.common.monitor.annotation;

import java.lang.annotation.*;

/**
 * @author Wisdom
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PrometheusMonitor {

    String apiName() default "";

    Type type() default Type.SUMMARY;

    enum Type {SUMMARY,COUNTER};
}