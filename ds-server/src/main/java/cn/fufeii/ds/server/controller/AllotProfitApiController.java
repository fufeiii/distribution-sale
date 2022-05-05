package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.model.PageRequest;
import cn.fufeii.ds.common.model.PageResult;
import cn.fufeii.ds.server.model.api.request.ProfitTradeRequest;
import cn.fufeii.ds.server.model.api.response.AllotProfitEventInfoResponse;
import cn.fufeii.ds.server.model.api.response.ProfitIncomeRecordResponse;
import cn.fufeii.ds.server.model.api.response.ProfitTradeResponse;
import cn.fufeii.ds.server.service.AllotProfitService;
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
@RequestMapping("/api/allot-profit")
public class AllotProfitApiController {

    @Autowired
    private AllotProfitService allotProfitService;

    @ApiOperation("发起金钱交易分润")
    @PostMapping("/trade")
    public CommonResult<ProfitTradeResponse> trade(@RequestBody ProfitTradeRequest request) {
        return CommonResult.success(allotProfitService.trade(request));
    }

    @ApiOperation("分页查询会员分销记录")
    @GetMapping("/record/member/page/{memberUsername}")
    public PageResult<ProfitIncomeRecordResponse> memberRecord(@PathVariable String memberUsername, PageRequest pageRequest) {
        IPage<ProfitIncomeRecordResponse> pageResult = allotProfitService.memberRecord(memberUsername, pageRequest.getPage(), pageRequest.getSize());
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("查询分润事件")
    @GetMapping("/event/{eventId}")
    public CommonResult<AllotProfitEventInfoResponse> event(@PathVariable Long eventId) {
        return CommonResult.success(allotProfitService.event(eventId));
    }


}
