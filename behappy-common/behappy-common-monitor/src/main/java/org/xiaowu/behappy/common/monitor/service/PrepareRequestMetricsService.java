package org.xiaowu.behappy.common.monitor.service;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.xiaowu.behappy.common.monitor.metadata.ApiRequestMonitor;

/**
 * @author Wisdom
 */
@Service
@RequiredArgsConstructor
public class PrepareRequestMetricsService {

    private final ApiRequestDurationAnalysisExporter apiRequestDurationAnalysisExporter;

    @Async
    public void prepareSummaryMetrics(ApiRequestMonitor apiRequestMonitor) {
        try {
            apiRequestDurationAnalysisExporter.prepareSummaryMetrics(apiRequestMonitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    public void prepareCounterMetrics(ApiRequestMonitor apiRequestMonitor) {
        try {
            apiRequestDurationAnalysisExporter.prepareCounterMetrics(apiRequestMonitor);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}