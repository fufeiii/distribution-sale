package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.model.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;

/**
 * 分润记录 Request
 *
 * @author FuFei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@ApiModel
public class ProfitIncomeRecordQueryRequest extends PageRequest {

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