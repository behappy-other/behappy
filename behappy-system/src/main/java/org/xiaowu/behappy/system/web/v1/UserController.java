package org.xiaowu.behappy.system.web.v1;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.xiaowu.behappy.common.core.util.Response;
import org.xiaowu.behappy.system.dto.UserDto;
import org.xiaowu.behappy.system.dto.UserLoginDto;
import org.xiaowu.behappy.system.dto.UserPasswordDto;
import org.xiaowu.behappy.system.entity.UserEntity;
import org.xiaowu.behappy.system.service.UserService;
import org.xiaowu.behappy.system.vo.UserVo;

import javax.validation.Valid;
import java.util.Arrays;

import static org.xiaowu.behappy.system.constant.Constants.PASSWD;

/**
 * 用户管理
 * @author xiaowu
 */
@AllArgsConstructor
@RestController
@RequestMapping("/web/v1/user")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    /**
     * 登录
     * @apiNote
     * @author xiaowu
     * @param userLoginDto
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.system.vo.UserVo>
     */
    @PostMapping("/login")
    public Response<UserVo> login(@Valid @RequestBody UserLoginDto userLoginDto) {
        UserVo userVo = userService.login(userLoginDto);
        return Response.ok(userVo);
    }

    /**
     * 修改密码
     * @apiNote
     * @author xiaowu
     * @param userPasswordDto
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Boolean>
     */
    @PostMapping("/password")
    public Response<Boolean> password(@Valid @RequestBody UserPasswordDto userPasswordDto) {
        userService.updatePassword(userPasswordDto);
        return Response.ok(true);
    }

    /**
     * 保存或更新用户
     * @apiNote
     * @author xiaowu
     * @param userDto
     * @return org.xiaowu.behappy.common.core.util.Response<java.lang.Integer>
     */
    @PostMapping
    public Response<Boolean> saveOrUpdate(@RequestBody UserDto userDto) {
        // save
        if (ObjectUtil.isNull(userDto.getId())) {
            String password = passwordEncoder.encode(PASSWD);
            userDto.setPassword(password);
        }
        UserEntity userEntity = BeanUtil.copyProperties(userDto, UserEntity.class);
        return Response.ok(userService.saveOrUpdate(userEntity));
    }


    /**
     * 分页获取用户
     * @apiNote
     * @author xiaowu
     * @param page
     * @param username
     * @param email
     * @param address
     * @return org.xiaowu.behappy.common.core.util.Response<com.baomidou.mybatisplus.extension.plugins.pagination.Page < org.xiaowu.behappy.system.vo.UserVo>>
     */
    @GetMapping("/page")
    public Response<Page<UserVo>> findPage(Page page, @RequestParam(defaultValue = "") String username,
                                           @RequestParam(defaultValue = "") String email,
                                           @RequestParam(defaultValue = "") String address) {
        Page<UserVo> voPage = userService.findPage(page, username, email, address);
        return Response.ok(voPage);
    }

    /**
     * 根据ids删除
     * @apiNote
     * @author xiaowu
     * @param id
     * @return org.xiaowu.behappy.common.core.util.Response
     */
    @DeleteMapping
    public Response<Boolean> del(@RequestBody Long[] id) {
        return Response.ok(userService.removeByIds(Arrays.asList(id)));
    }

    /**
     * 根据用户名查寻
     * @apiNote
     * @author xiaowu
     * @param username
     * @return org.xiaowu.behappy.common.core.util.Response<org.xiaowu.behappy.system.vo.UserVo>
     */
    @GetMapping("/username")
    public Response<UserVo> findByUsername(@RequestParam String username) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        UserEntity one = userService.getOne(queryWrapper);
        UserVo userVo = BeanUtil.copyProperties(one, UserVo.class);
        return Response.ok(userVo);
    }
}
