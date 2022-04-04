package org.xiaowu.behappy.product.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * save or update 品类
 * @author xiaowu
 */
@Data
public class SaveOrUpdateCategoryDto {

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 分类父id
     */
    private Long parentId = 0L;

    /**
     * 分类名称
     */
    @NotEmpty
    private String categoryName;

    /**
     * 分类图片
     */
    @NotEmpty
    private String pic;


    /**
     * 默认是1，表示正常状态,0为下线状态
     */
    @NotNull
    private Integer status;

    /**
     * 排序
     */
    private Integer seq = 0;


    /**
     * 分类层级
     */
    @NotNull
    private Integer grade;
}
