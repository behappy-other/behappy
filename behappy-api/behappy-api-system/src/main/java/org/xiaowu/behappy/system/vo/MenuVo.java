package org.xiaowu.behappy.system.vo;

import lombok.Data;

import java.util.List;

/**
 * Menu对象
 * @author xiaowu
 */
@Data
public class MenuVo {
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

    /**
     * children
     */
    private List<MenuVo> children;

}
