package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分销记录 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class ProfitIncomeRecordQueryRequest {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "页数")
    private Integer size = 10;

    @ApiModelProperty(value = "事件主键")
    private Long profitEventId;

    @ApiModelProperty(value = "账户类型")
    private String accountType;

    @ApiModelProperty(value = "获利会员主键")
    private Long impactMemberId;

    @ApiModelProperty(value = "获利数")
    private Integer incomeAmount;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}