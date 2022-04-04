package org.xiaowu.behappy.common.monitor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.DistributionSummary;
import io.micrometer.core.instrument.Metrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.xiaowu.behappy.common.monitor.metadata.ApiRequestMonitor;

import java.time.Duration;

/**
 * @author Wisdom
 */
@Slf4j
@Component
public class ApiRequestDurationAnalysisExporter {
    private final String HTTP_REQUEST_DURATION = "http.request.duration";

    private final String HTTP_REQUEST_COUNT = "http.request.count";

    private final String REQUEST_ANALYSIS_METRIC_BASE_UNIT = "seconds";

    private final String REQUEST_ANALYSIS_METRIC_DESCRIPTION = "behappy api request analysis";

    private final String API_NAME = "api_name";

    private final String RESPONSE_CODE = "response_code";
    /**
     * 每条样本数据的生命周期, unit:hours
     */
    private final Long REQUEST_ANALYSIS_METRIC_QUANTILE_DURATION = 6L;

    @Autowired
    private ObjectMapper objectMapper;

    private DistributionSummary distributionSummary;

    private Counter counter;

    void prepareSummaryMetrics(ApiRequestMonitor apiRequestMonitor) {

        try {
            log.info("prepareSummaryMetrics metric:{}", objectMapper.writeValueAsString(apiRequestMonitor));
        } catch (JsonProcessingException ignore) {
        }
        /**
         * baseUnit():设置指标的计算单位, 毫秒 秒 分钟 字节等
         * description():定义指标描述
         * publishPercentiles():设置summary指标统计计时的分布维度
         * distributionStatisticExpiry(Duration.XX):设置Summary指标分布维度的生命周期,分布摘要产生的统计信息会随着时间的推移而衰减,新的样本数据将会被赋予更大的权重
         * tag():新增label
         * register():将指标注册到全局注册器
         */
        distributionSummary =
                DistributionSummary.builder(HTTP_REQUEST_DURATION)
                        .baseUnit(REQUEST_ANALYSIS_METRIC_BASE_UNIT)
                        .description(REQUEST_ANALYSIS_METRIC_DESCRIPTION)
                        .publishPercentiles(Quantile.QUANTILE)
                        .distributionStatisticExpiry(Duration.ofHours(REQUEST_ANALYSIS_METRIC_QUANTILE_DURATION))
                        .tag(API_NAME, apiRequestMonitor.getApiName())
                        .tag(RESPONSE_CODE, apiRequestMonitor.getCode())
                        .register(Metrics.globalRegistry);

        distributionSummary.record(apiRequestMonitor.getElapsedTime() / 1000D);
    }

    void prepareCounterMetrics(ApiRequestMonitor apiRequestMonitor) {
        try {
            log.info("prepareCounterMetrics metric:{}", objectMapper.writeValueAsString(apiRequestMonitor));
        } catch (JsonProcessingException ignore) {
        }
        counter = Counter.builder(HTTP_REQUEST_COUNT)
                .baseUnit(REQUEST_ANALYSIS_METRIC_BASE_UNIT)
                .description(REQUEST_ANALYSIS_METRIC_DESCRIPTION)
                .tag(API_NAME, apiRequestMonitor.getApiName())
                .register(Metrics.globalRegistry);
        counter.increment();
    }
}

//分布
final class Quantile {

    static final double[] QUANTILE = new double[]{0.1, 0.3, 0.5, 0.7, 0.9, 0.95};

    private Quantile() {
    }
}

