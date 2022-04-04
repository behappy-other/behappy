package org.xiaowu.behappy.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Menu对象
 * @author xiaowu
 */
@Data
public class MenuDto implements Serializable {

    private static final long serialVersionUID = -2022856199838787490L;
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
     * isShow
     */
    private Integer isShow;

}
