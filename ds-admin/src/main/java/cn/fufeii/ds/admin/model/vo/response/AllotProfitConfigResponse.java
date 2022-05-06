package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分润配置 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class AllotProfitConfigResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "平台标识")
    private String platformUsername;

    @ApiModelProperty(value = "平台名称")
    private String platformNickname;

    @ApiModelProperty(value = "账户类型")
    private String accountType;

    @ApiModelProperty(value = "分润类型")
    private String profitType;

    @ApiModelProperty(value = "计算方式")
    private String calculateMode;

    @ApiModelProperty(value = "分润等级")
    private String profitLevel;

    @ApiModelProperty(value = "分润比例")
    private Integer profitRatio;

    @ApiModelProperty(value = "会员类型")
    private String memberIdentityType;

    @ApiModelProperty(value = "会员段位")
    private String memberRankType;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "更新时间")
    private Date updateDateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}