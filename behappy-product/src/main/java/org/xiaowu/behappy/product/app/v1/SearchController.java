package org.xiaowu.behappy.product.app.v1;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.product.service.ProdService;
import org.xiaowu.behappy.product.vo.SearchProdVo;

/**
 * @author xiaowu
 * 商品搜索
 */
@RestController
@RequestMapping("/app/v1/search")
@AllArgsConstructor
public class SearchController {

    private final ProdService prodService;

    /**
     * 分页排序搜索商品
     * @param page
     * @param prodName 商品名称
     * @param sort     排序(0 默认排序 1销量排序 2价格排序)
     * @param orderBy  排序(0升序 1降序)
     * @return org.xiaowu.behappy.common.core.util.Response<com.baomidou.mybatisplus.extension.plugins.pagination.Page < org.xiaowu.behappy.product.vo.SearchProdVo>>
     * @author xiaowu
     */
    @GetMapping("/page")
    public Response<Page<SearchProdVo>> searchProdPage(Page page, @RequestParam("prodName") String prodName,
                                                       @RequestParam("sort") Integer sort, @RequestParam("sort") Integer orderBy) {
        Page<SearchProdVo> voPage = prodService.searchProdPageByProdName(page, prodName, sort, orderBy);
        return Response.ok(voPage);
    }
}
