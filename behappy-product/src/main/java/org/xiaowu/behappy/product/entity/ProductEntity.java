package org.xiaowu.behappy.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.xiaowu.common.mybatis.base.BaseEntity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xiaowu
 */
@Data
@TableName("bh_p_prod")
@EqualsAndHashCode(callSuper = true)
public class ProductEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 62388974118227444L;

    /**
     * 商品ID
     */
    @TableId
    private Long prodId;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 原价
     * 冗余字段(方便查询,这里是上架多个sku时候的最低价格)
     */
    private BigDecimal oriPrice;

    /**
     * 现价
     * 冗余字段(方便查询,这里是上架多个sku时候的最低价格)
     */
    private BigDecimal price;

    /**
     * 简要描述,卖点等
     */
    private String brief;

    /**
     * 商品主图
     */
    private String pic;

    /**
     * 商品图片
     */
    private String imgs;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Integer status;

    /**
     * 商品分类
     */
    private Long categoryId;

    /**
     * 详细描述
     */
    private String content;

    /**
     * 上架时间
     */
    private LocalDateTime putAwayTime;

    /**
     * 已经销售数量
     */
    private Integer soldNum;

    /**
     * 每次去拿数据的时候都认为别人不会修改，觉得不值得上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据
     * 比起悲观锁,乐观锁适用于多读的应用类型，这样可以提高吞吐量
     */
    @Version
    private Integer version;

}
