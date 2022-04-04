package org.xiaowu.behappy.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * Role对象
 * @author xiaowu
 */
@Data
public class RoleDto implements Serializable {

    private static final long serialVersionUID = -3531839552650257311L;
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 唯一标识
     */
    private String flag;

}
