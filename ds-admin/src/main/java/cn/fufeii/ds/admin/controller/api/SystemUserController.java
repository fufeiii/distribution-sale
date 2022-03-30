package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.SystemUserQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.SystemUserResponse;
import cn.fufeii.ds.admin.service.SystemUserService;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 API Controller
 *
 * @author FuFei
 * @date 2022/3/27
 */
@Api(tags = "用户管理")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/system-user")
public class SystemUserController {
    @Autowired
    private SystemUserService systemUserService;

    @ApiOperation("查询当前用户")
    @GetMapping("/current")
    public CommonResult<SystemUserResponse> current() {
        return CommonResult.success(systemUserService.current());
    }

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<SystemUserResponse> page(@RequestBody SystemUserQueryRequest pageParam) {
        IPage<SystemUserResponse> pageResult = systemUserService.page(pageParam, new Page<SystemUser>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }


}