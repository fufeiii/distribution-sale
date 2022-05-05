package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分润记录 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class ProfitIncomeRecordResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "事件主键")
    private Long profitEventId;

    @ApiModelProperty(value = "账户类型")
    private String accountType;

    @ApiModelProperty(value = "获利会员主键")
    private Long impactMemberId;

    @ApiModelProperty(value = "获利数")
    private Integer incomeCount;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}