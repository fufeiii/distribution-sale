package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.server.model.api.request.MemberCreateRequest;
import cn.fufeii.ds.server.model.api.request.MemberIdentityTypeRequest;
import cn.fufeii.ds.server.model.api.request.MemberTeamRequest;
import cn.fufeii.ds.server.model.api.response.MemberCreateResponse;
import cn.fufeii.ds.server.model.api.response.MemberTeamResponse;
import cn.fufeii.ds.server.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("/info")
    public CommonResult<MemberCreateResponse> info(@RequestBody MemberCreateRequest request) {
        return CommonResult.success();
    }

    @ApiOperation("查询会员团队")
    @PostMapping("/team")
    public CommonResult<MemberTeamResponse> team(@RequestBody MemberTeamRequest request) {
        return CommonResult.success();
    }

    @ApiOperation(value = "更新会员身份")
    @PutMapping("/identity_type")
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
