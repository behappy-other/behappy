package org.xiaowu.behappy.thirdparty.vo;

import lombok.Data;

/**
 * oss datavo
 * @author xiaowu
 */
@Data
public class PolicyVo {
    private String accessId;

    private String policy;

    private String signature;

    private String dir;

    private String host;

    private String expire;
}
