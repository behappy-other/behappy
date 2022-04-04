package org.xiaowu.behappy.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_p_category")
@EqualsAndHashCode(callSuper = true)
public class CategoryEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 4274840438128394163L;
    /**
     * 类目ID
     */
    @TableId
    private Long categoryId;


    /**
     * 父节点
     */
    private Long parentId = 0L;

    /**
     * 产品类目名称
     */
    private String categoryName;

    /**
     * 类目图标
     */
    private String icon;

    /**
     * 类目的显示图片
     */
    private String pic;

    /**
     * 排序
     */
    private Integer seq;

    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    private Integer status;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;

    /**
     * 分类层级
     */
    private Integer grade;

    /**
     * 每次去拿数据的时候都认为别人不会修改，觉得不值得上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据
     * 比起悲观锁,乐观锁适用于多读的应用类型，这样可以提高吞吐量
     */
    @Version
    private Integer version;

}