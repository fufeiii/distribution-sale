package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.AllotProfitConfigQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.AllotProfitConfigUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.AllotProfitConfigResponse;
import cn.fufeii.ds.admin.service.AllotProfitConfigService;
import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.model.PageResult;
import cn.fufeii.ds.repository.entity.AllotProfitConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分润配置 API Controller
 *
 * @author FuFei
 */
@Api(tags = "分润配置")
@DataValid
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/allot-profit-config")
public class AllotProfitConfigController {

    @Autowired
    private AllotProfitConfigService allotProfitConfigService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<AllotProfitConfigResponse> page(@RequestBody AllotProfitConfigQueryRequest pageParam) {
        IPage<AllotProfitConfigResponse> pageResult = allotProfitConfigService.page(pageParam, new Page<AllotProfitConfig>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("详情查询")
    @GetMapping("/get/{id}")
    public CommonResult<AllotProfitConfigResponse> get(@PathVariable Long id) {
        return CommonResult.success(allotProfitConfigService.get(id));
    }

    @ApiOperation("新增")
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody AllotProfitConfigUpsertRequest createParam) {
        allotProfitConfigService.create(createParam);
        return CommonResult.success();
    }

    @ApiOperation("修改")
    @PutMapping("/modify")
    public CommonResult<Void> modify(@RequestBody AllotProfitConfigUpsertRequest modifyParam) {
        allotProfitConfigService.modify(modifyParam);
        return CommonResult.success();
    }

    @ApiOperation("删除")
    @DeleteMapping("/remove/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        allotProfitConfigService.remove(id);
        return CommonResult.success();
    }

    @ApiOperation("启用")
    @PutMapping("/enable/{id}")
    public CommonResult<Void> enable(@PathVariable Long id) {
        allotProfitConfigService.changeState(id, StateEnum.ENABLE);
        return CommonResult.success();
    }

    @ApiOperation("禁用")
    @PutMapping("/disable/{id}")
    public CommonResult<Void> disable(@PathVariable Long id) {
        allotProfitConfigService.changeState(id, StateEnum.DISABLE);
        return CommonResult.success();
    }

}