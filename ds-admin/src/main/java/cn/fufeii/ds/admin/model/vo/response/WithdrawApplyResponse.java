package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 提现申请 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class WithdrawApplyResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户主键")
    private Long memberId;

    @ApiModelProperty(value = "提现单号")
    private String withdrawNumber;

    @ApiModelProperty(value = "提现金额")
    private Integer withdrawAmount;

    @ApiModelProperty(value = "手续费")
    private Integer feeAmount;

    @ApiModelProperty(value = "状态")
    private String withdrawState;

    @ApiModelProperty(value = "提现描述")
    private String withdrawDesc;

    @ApiModelProperty(value = "审批时间")
    private Date approvalTime;

    @ApiModelProperty(value = "审批描述")
    private String approvalDesc;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}