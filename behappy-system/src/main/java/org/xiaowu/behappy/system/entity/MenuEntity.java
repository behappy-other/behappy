package org.xiaowu.behappy.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * Menu对象
 * @author xiaowu
 */
@Data
@TableName("bh_sys_menu")
@EqualsAndHashCode(callSuper = true)
public class MenuEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = -5374498036028931415L;

    @TableId
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 路径
     */
    private String path;

    /**
     * 图标
     */
    private String icon;

    /**
     * 描述
     */
    private String description;

    /**
     * pid
     */
    private Long pid;

    /**
     * pagePath
     */
    private String pagePath;

    /**
     * sortNum
     */
    private Integer sortNum;

    /**
     * 是否展示
     * 1 true
     * 0 false
     */
    private Integer isShow;


}
