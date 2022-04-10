package cn.fufeii.ds.server.model.api.response;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 分销事件 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class ProfitEventInfoResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "分润类型")
    private ProfitTypeEnum profitType;

    @ApiModelProperty(value = "事件触发会员主键")
    private Long triggerMemberId;

    @ApiModelProperty(value = "事件编号")
    private String eventNumber;

    @ApiModelProperty(value = "事件金额")
    private Integer eventAmount;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建事件")
    private Date createDateTime;

    @ApiModelProperty(value = "分销记录")
    private List<ProfitRecordInfo> profitRecordInfoList;

    @Data
    public static class ProfitRecordInfo {

        @ApiModelProperty(value = "主键")
        private Long id;

        @ApiModelProperty(value = "事件主键")
        private Long profitEventId;

        @ApiModelProperty(value = "账户类型")
        private AccountTypeEnum accountType;

        @ApiModelProperty(value = "获利会员主键")
        private Long impactMemberId;

        @ApiModelProperty(value = "获利数(分/个)")
        private Integer incomeCount;

        @ApiModelProperty(value = "备注")
        private String memo;

        @ApiModelProperty(value = "创建时间")
        private Date createDateTime;

    }
}