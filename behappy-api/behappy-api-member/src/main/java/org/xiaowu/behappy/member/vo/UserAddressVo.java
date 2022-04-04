package org.xiaowu.behappy.member.vo;

import lombok.Data;

/**
 * @author xiaowu
 * 用户地址列表返回
 */
@Data
public class UserAddressVo {
    /**
     * 地址id
     */
    private Long addrId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 区
     */
    private String area;

    /**
     * 地址
     */
    private String addr;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 是否默认地址（1:是 0：否）
     */
    private Integer commonAddr;

}
