package org.xiaowu.common.mybatis.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiaowu
 * @date 12:52
 * @apiNote
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -3074132056928885100L;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    ///**
    // * 创建者
    // */
    //private String createBy;
    //
    //
    ///**
    // * 更新者
    // */
    //private String updateBy;

}
