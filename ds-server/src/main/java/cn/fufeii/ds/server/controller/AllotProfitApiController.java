package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.server.model.api.request.ProfitTradeRequest;
import cn.fufeii.ds.server.model.api.response.AllotProfitEventInfoResponse;
import cn.fufeii.ds.server.model.api.response.ProfitTradeResponse;
import cn.fufeii.ds.server.service.AllotProfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分润 API controller
 *
 * @author FuFei
 */
@Api(tags = "分润API")
@DataValid
@RestController
@RequestMapping("/api/allot-profit")
public class AllotProfitApiController {

    @Autowired
    private AllotProfitService allotProfitService;

    @ApiOperation("发起金钱交易")
    @PostMapping("/trade")
    public CommonResult<ProfitTradeResponse> trade(@RequestBody ProfitTradeRequest request) {
        return CommonResult.success(allotProfitService.trade(request));
    }

    @ApiOperation("查询分润事件")
    @GetMapping("/event/{eventId}")
    public CommonResult<AllotProfitEventInfoResponse> event(@PathVariable Long eventId) {
        return CommonResult.success(allotProfitService.event(eventId));
    }


}
