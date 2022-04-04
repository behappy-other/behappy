package org.xiaowu.behappy.system.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_sys_dict")
public class DictEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3081666116621733032L;

    @TableId
    private Long id;

    private String name;

    private String value;

    private String type;

}