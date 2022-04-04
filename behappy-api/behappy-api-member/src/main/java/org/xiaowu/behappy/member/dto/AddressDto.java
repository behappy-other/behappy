package org.xiaowu.behappy.member.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 用户配送地址参数
 * @author xiaowu
 */
@Data
public class AddressDto {

    /**
     * 地址ID
     */
    @NotNull
    private Long addrId;

    /**
     * 收货人
     */
    @NotNull(message = "收货人不能为空")
    private String receiver;

    /**
     * 地址
     */
    @NotNull(message = "地址不能为空")
    private String addr;

    /**
     * 手机
     */
    @NotNull(message = "手机不能为空")
    private String mobile;

    /**
     * 省ID
     */
    @NotNull(message = "省ID不能为空")
    private Long provinceId;

    /**
     * 城市ID
     */
    @NotNull(message = "城市ID不能为空")
    private Long cityId;

    /**
     * 区ID
     */
    @NotNull(message = "区ID不能为空")
    private Long areaId;

    /**
     * 省
     */
    @NotNull(message = "省不能为空")
    private String province;

    /**
     * 城市
     */
    @NotNull(message = "城市不能为空")
    private String city;

    /**
     * 区
     */
    @NotNull(message = "区不能为空")
    private String area;
}