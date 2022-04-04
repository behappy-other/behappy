package org.xiaowu.behappy.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * Role对象
 */
@Data
@TableName("bh_sys_role")
@EqualsAndHashCode(callSuper = true)
public class RoleEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4579221181022070791L;
    @TableId
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
