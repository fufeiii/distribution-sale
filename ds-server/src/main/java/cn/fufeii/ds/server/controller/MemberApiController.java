package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.model.PageRequest;
import cn.fufeii.ds.common.model.PageResult;
import cn.fufeii.ds.server.model.api.request.MemberCreateRequest;
import cn.fufeii.ds.server.model.api.request.MemberIdentityTypeRequest;
import cn.fufeii.ds.server.model.api.response.MemberCreateResponse;
import cn.fufeii.ds.server.model.api.response.MemberResponse;
import cn.fufeii.ds.server.model.api.response.MemberTeamResponse;
import cn.fufeii.ds.server.service.MemberService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * 会员 API controller
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Api(tags = "会员API")
@DataValid
@RestController
@RequestMapping("/api/member")
public class MemberApiController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("创建会员")
    @PostMapping("/create")
    public CommonResult<MemberCreateResponse> create(@RequestBody MemberCreateRequest request) {
        return CommonResult.success(memberService.create(request));
    }

    @ApiOperation("查询会员详情")
    @GetMapping("/info/{username}")
    public CommonResult<MemberResponse> info(@PathVariable String username) {
        return CommonResult.success(memberService.info(username));
    }

    @ApiOperation("分页查询会员团队")
    @GetMapping("/team/page/{level}/{username}")
    public PageResult<MemberTeamResponse> team(@PathVariable String level, @PathVariable String username, PageRequest pageRequest) {
        Optional<ProfitLevelEnum> profitLevelEnumOptional = ProfitLevelEnum.getByNameOptional(level);
        if (!profitLevelEnumOptional.isPresent()) {
            throw new BizException(ExceptionEnum.CLIENT_ERROR, "level参数错误");
        }
        ProfitLevelEnum profitLevelEnum = profitLevelEnumOptional.get();
        if (ProfitLevelEnum.SELF == profitLevelEnum) {
            throw new BizException(ExceptionEnum.CLIENT_ERROR, "level参数不能为self");
        }
        IPage<MemberTeamResponse> pageResult = memberService.team(profitLevelEnum, username, pageRequest.getPage(), pageRequest.getSize());
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation(value = "更新会员身份")
    @PutMapping("/identity-type")
    public CommonResult<Void> identityType(@RequestBody MemberIdentityTypeRequest identityType) {
        memberService.identityType(identityType);
        return CommonResult.success();
    }

    @ApiOperation(value = "启用会员")
    @PutMapping("/enable/{username}")
    public CommonResult<Void> enable(@PathVariable String username) {
        memberService.changeState(username, StateEnum.ENABLE);
        return CommonResult.success();
    }

    @ApiOperation("禁用会员")
    @PutMapping("/disable/{username}")
    public CommonResult<Void> disable(@PathVariable String username) {
        memberService.changeState(username, StateEnum.DISABLE);
        return CommonResult.success();
    }


}
