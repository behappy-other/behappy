package org.xiaowu.behappy.common.monitor.config;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.monitor.annotation.PrometheusMonitor;
import org.xiaowu.behappy.common.monitor.metadata.ApiRequestMonitor;
import org.xiaowu.behappy.common.monitor.service.PrepareRequestMetricsService;


/**
 * @author Wisdom
 */
@Component
@Aspect
@RequiredArgsConstructor
public class PrometheusApiMonitorAspect {

    private final PrepareRequestMetricsService prepareRequestMetricsService;

    @Pointcut("@annotation(org.xiaowu.behappy.common.monitor.annotation.PrometheusMonitor)")
    public void preBusinessProcess(){}

    @Around("preBusinessProcess()")
    public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
        PrometheusMonitor prometheusAnnotation = ((MethodSignature)pjp.getSignature()).getMethod().getAnnotation(PrometheusMonitor.class);
        StopWatch stopWatch = new StopWatch();
        Object obj = pjp.proceed();
        Response response = (Response) obj;
        ApiRequestMonitor apiRequestMonitor = ApiRequestMonitor.builder()
                .apiName(prometheusAnnotation.apiName())
                .code(String.valueOf(response.getCode()))
                .elapsedTime(stopWatch.getTotalTimeMillis()).build();
        if (prometheusAnnotation.type()== PrometheusMonitor.Type.SUMMARY){
            prepareRequestMetricsService.prepareSummaryMetrics(apiRequestMonitor);
        }else {
            prepareRequestMetricsService.prepareCounterMetrics(apiRequestMonitor);
        }
        return obj;
    }
}