package org.xiaowu.behappy.member.app.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.member.service.RegionService;
import org.xiaowu.behappy.member.vo.RegionVo;

import java.util.List;

/**
 * 获取地区数据
 * @author xiaowu
 */
@RestController
@RequestMapping("/app/v1/region")
@RequiredArgsConstructor
public class RegionController {

    private final RegionService regionService;

    /**
     * 根据pid获取地区数据信息
     * @author xiaowu
     * @param pid 省市区的pid(pid为0的话则获取所有省份)
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.member.vo.RegionVo>>
     */
    @GetMapping("/list")
    public Response<List<RegionVo>> regions(@RequestParam("pid") Long pid) {
        List<RegionVo> regionVos = regionService.regionsByPid(pid);
        return Response.ok(regionVos);
    }
}
