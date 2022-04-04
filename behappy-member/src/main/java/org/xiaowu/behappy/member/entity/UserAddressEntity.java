package org.xiaowu.behappy.member.entity;

/**
 * @author xiaowu
 * @apiNote
 */

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_u_address")
@EqualsAndHashCode(callSuper = true)
public class UserAddressEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -4338009469281277840L;
    /**
     * ID
     */
    @TableId
    private Long addrId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 收货人
     */
    private String receiver;

    /**
     * 省ID
     */
    private Long provinceId;

    /**
     * 省
     */
    private String province;

    /**
     * 城市
     */
    private String city;

    /**
     * 城市ID
     */
    private Long cityId;

    /**
     * 区
     */
    private String area;

    /**
     * 区ID
     */
    private Long areaId;

    /**
     * 地址
     */
    private String addr;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 是否默认地址 1是
     */
    private Integer commonAddr;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

}
