package org.xiaowu.behappy.system.web.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.system.service.DictService;
import org.xiaowu.behappy.system.vo.DictVo;

import java.util.List;

/**
 *
 * @author xiaowu
 */
@RestController
@RequestMapping("/web/v1/dict")
@AllArgsConstructor
public class DictController {

    private final DictService dictService;

    /**
     * 根据type获取dict数据
     * @apiNote
     * @author xiaowu
     * @param type
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.system.vo.DictVo>>
     */
    @GetMapping
    public Response<List<DictVo>> dictVoResponse(@RequestParam String type) {
        List<DictVo> dictVos = dictService.dictVoResponse(type);
        return Response.ok(dictVos);
    }
}
