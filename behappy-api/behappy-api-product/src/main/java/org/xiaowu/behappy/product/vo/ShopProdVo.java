package org.xiaowu.behappy.product.vo;

import lombok.Data;

/**
 * 购物车加载商品信息
 * @author xiaowu
 */
@Data
public class ShopProdVo {

    private Long prodId;

    private String brief;

    private String pic;

    /**
     * 默认是1，表示正常状态, -1表示删除, 0下架
     */
    private Integer status;
}
