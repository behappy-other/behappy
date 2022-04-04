package org.xiaowu.behappy.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * 省市区县乡镇三级城市数据
 *
 * @author xiaowu
 */
@Data
@TableName("bh_u_region")
@EqualsAndHashCode(callSuper = true)
public class RegionEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1440182634331472457L;

    /**
     * 地区id
     */
    @TableId
    private Long areaId;

    /**
     * 地区名称
     */
    private String areaName;

    /**
     * 地区上级id
     */
    private Long parentId;

    /**
     * 地区层级
     */
    private Integer level;
}
