package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.model.vo.request.MemberRankConfigQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.MemberRankConfigUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.MemberRankConfigResponse;
import cn.fufeii.ds.admin.service.MemberRankConfigService;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.model.PageResult;
import cn.fufeii.ds.repository.entity.MemberRankConfig;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 段位配置 API Controller
 *
 * @author FuFei
 */
@Api(tags = "段位配置")
@RestController
@RequestMapping("/admin/member-rank-config")
public class MemberRankConfigController {

    @Autowired
    private MemberRankConfigService memberRankConfigService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<MemberRankConfigResponse> page(@RequestBody MemberRankConfigQueryRequest pageParam) {
        IPage<MemberRankConfigResponse> pageResult = memberRankConfigService.page(pageParam, new Page<MemberRankConfig>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("详情查询")
    @GetMapping("/get/{id}")
    public CommonResult<MemberRankConfigResponse> get(@PathVariable Long id) {
        return CommonResult.success(memberRankConfigService.get(id));
    }

    @ApiOperation("新增")
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody MemberRankConfigUpsertRequest createParam) {
        memberRankConfigService.create(createParam);
        return CommonResult.success();
    }

    @ApiOperation("修改")
    @PutMapping("/modify")
    public CommonResult<Void> modify(@RequestBody MemberRankConfigUpsertRequest modifyParam) {
        memberRankConfigService.modify(modifyParam);
        return CommonResult.success();
    }

    @ApiOperation("删除")
    @DeleteMapping("/remove/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        memberRankConfigService.remove(id);
        return CommonResult.success();
    }

    @ApiOperation("启用")
    @PutMapping("/enable/{id}")
    public CommonResult<Void> enable(@PathVariable Long id) {
        memberRankConfigService.changeState(id, StateEnum.ENABLE);
        return CommonResult.success();
    }

    @ApiOperation("禁用")
    @PutMapping("/disable/{id}")
    public CommonResult<Void> disable(@PathVariable Long id) {
        memberRankConfigService.changeState(id, StateEnum.DISABLE);
        return CommonResult.success();
    }

}