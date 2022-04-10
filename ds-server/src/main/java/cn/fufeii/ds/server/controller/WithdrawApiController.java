package cn.fufeii.ds.server.controller;


import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.server.model.api.response.WithdrawApplyInfoResponse;
import cn.fufeii.ds.server.service.WithdrawService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 提现 API Controller
 *
 * @author FuFei
 */
@Api(tags = "提现Api")
@DataValid
@RestController
@RequestMapping("/api/withdraw-apply")
public class WithdrawApiController {
    @Autowired
    private WithdrawService withdrawService;

    @ApiOperation("发起提现申请")
    @PostMapping("/create")
    public CommonResult<Object> create() {
        return CommonResult.success();
    }

    @ApiOperation("查询提现申请详情")
    @PostMapping("/info/{withdrawNumber}")
    public CommonResult<WithdrawApplyInfoResponse> info(@PathVariable String withdrawNumber) {
        return CommonResult.success();
    }

    @ApiOperation("分页查询会员提现申请记录")
    @GetMapping("/member/page/{memberUsername}")
    public PageResult<WithdrawApplyInfoResponse> memberWithdrawApply(@PathVariable String memberUsername) {
        return PageResult.success(0, null);
    }

}