package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.enumerate.biz.*;
import cn.fufeii.ds.common.model.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 分润配置 Request
 *
 * @author FuFei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@ApiModel
public class AllotProfitConfigQueryRequest extends PageRequest {

    @ApiModelProperty(value = "账户类型")
    private AccountTypeEnum accountType;

    @ApiModelProperty(value = "分润类型")
    private ProfitTypeEnum profitType;

    @ApiModelProperty(value = "计算方式")
    private CalculateModeEnum calculateMode;

    @ApiModelProperty(value = "分润等级")
    private ProfitLevelEnum profitLevel;

    @ApiModelProperty(value = "会员类型")
    private MemberIdentityTypeEnum memberIdentityType;

    @ApiModelProperty(value = "会员段位")
    private MemberRankTypeEnum memberRankType;

    @ApiModelProperty(value = "状态")
    private StateEnum state;

}