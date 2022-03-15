package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.enumerate.biz.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分润参数 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class ProfitParamQueryRequest {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "页数")
    private Integer size = 10;

    @ApiModelProperty(value = "账户类型")
    private AccountTypeEnum accountType;

    @ApiModelProperty(value = "分润类型")
    private ProfitTypeEnum profitType;

    @ApiModelProperty(value = "计算方式")
    private CalculateModeEnum calculateMode;

    @ApiModelProperty(value = "分润等级")
    private ProfitLevelEnum profitLevel;

    @ApiModelProperty(value = "用户类型")
    private MemberIdentityTypeEnum memberIdentityType;

    @ApiModelProperty(value = "用户段位")
    private MemberRankTypeEnum memberRankType;

    @ApiModelProperty(value = "状态")
    private StateEnum state;

}