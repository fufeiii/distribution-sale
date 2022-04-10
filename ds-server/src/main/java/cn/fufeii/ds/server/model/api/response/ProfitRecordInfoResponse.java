package cn.fufeii.ds.server.model.api.response;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分销记录 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class ProfitRecordInfoResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "事件主键")
    private Long profitEventId;

    @ApiModelProperty(value = "账户类型")
    private AccountTypeEnum accountType;

    @ApiModelProperty(value = "获利会员主键")
    private Long impactMemberId;

    @ApiModelProperty(value = "获利会员标识")
    private String impactMemberUsername;

    @ApiModelProperty(value = "获利数(分/个)")
    private Integer incomeCount;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}