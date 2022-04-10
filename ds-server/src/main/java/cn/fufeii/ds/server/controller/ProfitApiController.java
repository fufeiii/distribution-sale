package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.server.model.api.request.ProfitTradeRequest;
import cn.fufeii.ds.server.model.api.response.ProfitEventInfoResponse;
import cn.fufeii.ds.server.model.api.response.ProfitRecordInfoResponse;
import cn.fufeii.ds.server.model.api.response.ProfitTradeResponse;
import cn.fufeii.ds.server.service.ProfitService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分润 API controller
 *
 * @author FuFei
 * @date 2022/4/10
 */
@Api(tags = "分润API")
@DataValid
@RestController
@RequestMapping("/api/profit")
public class ProfitApiController {

    @Autowired
    private ProfitService profitService;

    @ApiOperation("发起金钱交易分润")
    @PostMapping("/trade")
    public CommonResult<ProfitTradeResponse> trade(@RequestBody ProfitTradeRequest request) {
        return CommonResult.success(profitService.trade(request));
    }

    @ApiOperation("分页查询会员分销记录")
    @GetMapping("/record/member/page/{memberUsername}")
    public PageResult<ProfitRecordInfoResponse> record(@PathVariable String memberUsername, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer size) {
        IPage<ProfitRecordInfoResponse> pageResult = profitService.memberRecord(memberUsername, page, size);
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("查询分润事件")
    @GetMapping("/event/{eventId}")
    public CommonResult<ProfitEventInfoResponse> event(@PathVariable Long eventId) {
        return CommonResult.success(profitService.event(eventId));
    }


}
