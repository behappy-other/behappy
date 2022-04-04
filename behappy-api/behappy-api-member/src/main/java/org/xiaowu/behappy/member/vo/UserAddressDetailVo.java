package org.xiaowu.behappy.member.vo;

import lombok.Data;

/**
 * @author xiaowu
 * 用户地址详细信息返回
 */
@Data
public class UserAddressDetailVo {
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
     * 省ID
     */
    private Long provinceId;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 区域ID
     */
    private Long areaId;
}
