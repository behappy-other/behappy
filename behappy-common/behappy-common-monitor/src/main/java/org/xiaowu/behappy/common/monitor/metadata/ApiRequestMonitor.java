package org.xiaowu.behappy.common.monitor.metadata;

import lombok.Builder;
import lombok.Data;

/**
 * @author Wisdom
 */
@Data
@Builder
public class ApiRequestMonitor {

    private String apiName;

    private Long elapsedTime;

    private String code;

}