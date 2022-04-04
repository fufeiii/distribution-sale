package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.result.CommonResult;
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

    @ApiOperation("用户段位类型枚举")
    @GetMapping("/member-rank-type")
    public CommonResult<List<KeyValuePair<String, String>>> memberRankTypeEnum() {
        return CommonResult.success(MemberRankTypeEnum.getKeyValuePairList());
    }

    @ApiOperation("用户身份类型枚举")
    @GetMapping("/member-identity-type")
    public CommonResult<List<KeyValuePair<String, String>>> memberIdentityTypeEnum() {
        return CommonResult.success(MemberIdentityTypeEnum.getKeyValuePairList());
    }


}
