package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.server.model.api.request.ProfitTradeRequest;
import cn.fufeii.ds.server.service.ProfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @ApiOperation("金钱交易")
    @PostMapping("/trade")
    public CommonResult<Void> trade(@RequestBody ProfitTradeRequest request) {
        profitService.trade(request);
        return CommonResult.success();
    }

}
