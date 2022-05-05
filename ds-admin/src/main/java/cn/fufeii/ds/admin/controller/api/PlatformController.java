package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.PlatformQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.PlatformUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.PlatformResponse;
import cn.fufeii.ds.admin.service.PlatformService;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.model.PageResult;
import cn.fufeii.ds.repository.entity.Platform;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 平台信息 API Controller
 *
 * @author FuFei
 */
@Api(tags = "平台管理")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/platform")
public class PlatformController {

    @Autowired
    private PlatformService platformService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<PlatformResponse> page(@RequestBody PlatformQueryRequest pageParam) {
        IPage<PlatformResponse> pageResult = platformService.page(pageParam, new Page<Platform>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("列表查询")
    @GetMapping("/list")
    public PageResult<PlatformResponse> list() {
        IPage<PlatformResponse> pageResult = platformService.page(new PlatformQueryRequest(), new Page<Platform>(1, 10000).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("新增")
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody PlatformUpsertRequest request) {
        platformService.create(request);
        return CommonResult.success();
    }

    @ApiOperation("启用")
    @PutMapping("/enable/{id}")
    public CommonResult<Void> enable(@PathVariable Long id) {
        platformService.changeState(id, StateEnum.ENABLE);
        return CommonResult.success();
    }

    @ApiOperation("禁用")
    @PutMapping("/disable/{id}")
    public CommonResult<Void> disable(@PathVariable Long id) {
        platformService.changeState(id, StateEnum.DISABLE);
        return CommonResult.success();
    }

}