package org.xiaowu.behappy.member.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_u_user")
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 3634097908657102927L;
    /**
     * ID
     */
    @TableId
    private Long userId;

    /**
     * 用户昵称
     */
    private String nickName;

    /**
     * 用户注册时的唯一信息
     */
    private String principal;

    /**
     * 登录密码
     */
    private String credentials;


    /**
     * 手机号码
     */
    private String userMobile;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 注册时间
     */
    private LocalDateTime userRegtime;

    /**
     * 注册IP
     */
    private String userRegip;

    /**
     * 头像图片路径
     */
    private String pic;

    /**
     * 状态 1:正常 0:无效
     */
    private Integer status;


}