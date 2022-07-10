package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分润事件 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class AllotProfitEventResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "分润类型")
    private String profitType;

    @ApiModelProperty(value = "事件触发会员")
    private String triggerMember;

    @ApiModelProperty(value = "事件编号")
    private String eventNumber;

    @ApiModelProperty(value = "事件金额")
    private Integer eventAmount;

    @ApiModelProperty(value = "通知状态")
    private String notifyState;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建事件")
    private Date createDateTime;

}