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
@TableName("bh_p_sku")
@EqualsAndHashCode(callSuper = true)
public class SkuEntity extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -1684650369444829514L;
    /**
     * 单品ID
     */
    @TableId
    private Long skuId;

    /**
     * 商品ID
     */
    private Long prodId;

    /**
     * 销售属性组合字符串,格式是p1:v1;p2:v2
     */
    private String properties;

    /**
     * 原价
     */
    private BigDecimal oriPrice;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 记录时间
     */
    private LocalDateTime recTime;

    /**
     * sku图片
     */
    private String pic;

    /**
     * sku名称
     */
    private String skuName;

    /**
     * 商品名称
     */
    private String prodName;

    /**
     * 状态：0禁用 1 启用
     */
    private Integer status;

    /**
     * 0 正常 1 已被删除
     */
    private Integer isDelete;

    /**
     * 每次去拿数据的时候都认为别人不会修改，觉得不值得上锁，但是在更新的时候会判断一下在此期间别人有没有去更新这个数据
     * 比起悲观锁,乐观锁适用于多读的应用类型，这样可以提高吞吐量
     */
    @Version
    private Integer version;

}
