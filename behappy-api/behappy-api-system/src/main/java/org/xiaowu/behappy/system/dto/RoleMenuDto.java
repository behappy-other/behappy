package org.xiaowu.behappy.system.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author xiaowu
 */
@Data
public class RoleMenuDto implements Serializable {

    private static final long serialVersionUID = -4327708712631083101L;
    private Long roleId;

    private Long menuId;

}
