package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.service.SysUserService;
import cn.fufeii.ds.common.result.CommonResult;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户管理 API Controller
 *
 * @author FuFei
 * @date 2022/3/27
 */
@Api("用户管理")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/sys-user")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    /**
     * 查询当前用户
     */
    @GetMapping("/current")
    public CommonResult<Object> current() {
        return CommonResult.success(sysUserService.current());
    }

}