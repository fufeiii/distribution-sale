package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.common.enumerate.biz.*;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.util.KeyValuePair;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 枚举数据 API Controller
 *
 * @author FuFei
 * @date 2022/4/4
 */
@Api(tags = "枚举数据")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/enum")
public class EnumController {

    @ApiOperation("账户类型枚举")
    @GetMapping("/account-type")
    public CommonResult<List<KeyValuePair<String, String>>> accountTypeEnum() {
        return CommonResult.success(AccountTypeEnum.getKeyValuePairList());
    }

    @ApiOperation("分润类型枚举")
    @GetMapping("/profit-type")
    public CommonResult<List<KeyValuePair<String, String>>> profitTypeEnum() {
        return CommonResult.success(ProfitTypeEnum.getKeyValuePairList());
    }

    @ApiOperation("计算模型枚举")
    @GetMapping("/calculate-mode")
    public CommonResult<List<KeyValuePair<String, String>>> calculateModeEnum() {
        return CommonResult.success(CalculateModeEnum.getKeyValuePairList());
    }

    @ApiOperation("分润等级枚举")
    @GetMapping("/profit-level")
    public CommonResult<List<KeyValuePair<String, String>>> profitLevelEnum() {
        return CommonResult.success(ProfitLevelEnum.getKeyValuePairList());
    }

    @ApiOperation("会员段位类型枚举")
    @GetMapping("/member-rank-type")
    public CommonResult<List<KeyValuePair<String, String>>> memberRankTypeEnum() {
        return CommonResult.success(MemberRankTypeEnum.getKeyValuePairList());
    }

    @ApiOperation("会员身份类型枚举")
    @GetMapping("/member-identity-type")
    public CommonResult<List<KeyValuePair<String, String>>> memberIdentityTypeEnum() {
        return CommonResult.success(MemberIdentityTypeEnum.getKeyValuePairList());
    }

    @ApiOperation("状态枚举")
    @GetMapping("/state")
    public CommonResult<List<KeyValuePair<String, String>>> stateEnum() {
        return CommonResult.success(StateEnum.getKeyValuePairList());
    }


}
