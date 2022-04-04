package org.xiaowu.behappy.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_sys_role_menu")
@EqualsAndHashCode(callSuper = true)
public class RoleMenuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 8469042367158199764L;

    private Long roleId;

    private Long menuId;

}
