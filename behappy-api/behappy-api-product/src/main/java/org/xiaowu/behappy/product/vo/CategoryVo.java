package org.xiaowu.behappy.product.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.xiaowu.behappy.common.core.serializer.ImgJsonSerializer;

/**
 * 移动端分类接口返回消息体
 * @author xiaowu
 */
@Data
public class CategoryVo {

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类父id
     */
    private Long parentId;

    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类图片
     */
    @JsonSerialize(using = ImgJsonSerializer.class)
    private String pic;


    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer seq;

}