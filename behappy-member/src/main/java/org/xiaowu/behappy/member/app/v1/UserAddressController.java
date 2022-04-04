package org.xiaowu.behappy.member.app.v1;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.common.satoken.util.SaUtils;
import org.xiaowu.behappy.member.dto.AddressDto;
import org.xiaowu.behappy.member.service.UserAddressService;
import org.xiaowu.behappy.member.vo.UserAddressDetailVo;
import org.xiaowu.behappy.member.vo.UserAddressVo;

import javax.validation.Valid;
import java.util.List;

/**
 * @author xiaowu
 * 用户地址接口
 */
@RestController
@RequestMapping("/app/v1/address")
@AllArgsConstructor
public class UserAddressController {

    private final UserAddressService userAddressService;

    /**
     * 获取用户收获地址列表
     * @author xiaowu
     * @return org.xiaowu.behappy.common.core.util.Response<java.util.List < org.xiaowu.behappy.member.vo.UserAddressVo>>
     */
    @GetMapping("/list")
    public Response<List<UserAddressVo>> addAddressList() {
        Long userId = SaUtils.getUser().getUserId();
        List<UserAddressVo> userAddressVos = userAddressService.listAddressesByUserId(userId);
        return Response.ok(userAddressVos);
    }


    /**
     * 设置默认地址
     * @author xiaowu
     * @param addressId
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PutMapping("/default")
    public Response setDefaultAddress(@RequestParam("addressId") Long addressId) {
        Long userId = SaUtils.getUser().getUserId();
        userAddressService.setDefaultAddress(addressId, userId);
        return Response.ok();
    }


    /**
     * 获取地址信息
     * @author xiaowu
     * @param addressId: default 0
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.member.vo.UserAddressDetailVo>
     */
    @GetMapping("/detail")
    public Response<UserAddressDetailVo> detail(@RequestParam("addressId") Long addressId) {
        Long userId = SaUtils.getUser().getUserId();
        UserAddressDetailVo userAddressDetailVo = userAddressService.getDetailAddress(addressId, userId);
        return Response.ok(userAddressDetailVo);
    }


    /**
     * 添加配送地址
     * @author xiaowu
     * @param addressDto
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PostMapping
    public Response addAddress(@Valid @RequestBody AddressDto addressDto) {
        Long userId = SaUtils.getUser().getUserId();
        userAddressService.addAddress(addressDto, userId);
        return Response.ok();
    }

    /**
     * 修改配送地址
     * @author xiaowu
     * @param addressDto
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @PutMapping
    public Response updateAddress(@Valid @RequestBody AddressDto addressDto) {
        Long userId = SaUtils.getUser().getUserId();
        userAddressService.updateAddress(addressDto, userId);
        return Response.ok();
    }


    /**
     * 删除配送地址
     * @author xiaowu
     * @param addressId
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @DeleteMapping
    public Response delAddress(@RequestParam("addressId") Long addressId) {
        Long userId = SaUtils.getUser().getUserId();
        userAddressService.delAddress(addressId, userId);
        return Response.ok();
    }
}
