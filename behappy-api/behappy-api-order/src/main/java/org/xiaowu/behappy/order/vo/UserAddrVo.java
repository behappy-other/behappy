package org.xiaowu.behappy.order.vo;

import lombok.Data;

/**
 * 用户配送地址,用于/confirm接口
 * @author xiaowu
 */
@Data
public class UserAddrVo {
    /**
     * 接收人
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

}
