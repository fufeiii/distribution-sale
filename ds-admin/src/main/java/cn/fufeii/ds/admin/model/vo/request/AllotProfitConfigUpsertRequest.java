package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.enumerate.biz.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * 分润参数 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class AllotProfitConfigUpsertRequest {

    @ApiModelProperty(value = "主键（修改时必传）")
    private Long id;

    @NotNull
    @ApiModelProperty(value = "账户类型")
    private AccountTypeEnum accountType;

    @NotNull
    @ApiModelProperty(value = "分润类型")
    private ProfitTypeEnum profitType;

    @NotNull
    @ApiModelProperty(value = "计算方式")
    private CalculateModeEnum calculateMode;

    @NotNull
    @ApiModelProperty(value = "分润等级")
    private ProfitLevelEnum profitLevel;

    @NotNull
    @Range(min = 1, max = 100)
    @ApiModelProperty(value = "分润比例")
    private Integer profitRatio;

    @NotNull
    @ApiModelProperty(value = "会员类型")
    private MemberIdentityTypeEnum memberIdentityType;

    @NotNull
    @ApiModelProperty(value = "会员段位")
    private MemberRankTypeEnum memberRankType;

}