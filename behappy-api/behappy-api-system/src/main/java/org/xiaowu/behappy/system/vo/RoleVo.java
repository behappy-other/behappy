package org.xiaowu.behappy.system.vo;

import lombok.Data;

/**
 * Role对象
 * @author xiaowu
 */
@Data
public class RoleVo {

    private Long id;

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
