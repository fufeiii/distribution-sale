package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员账户 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class AccountResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员主键")
    private Long memberId;

    @ApiModelProperty(value = "总金额")
    private Integer moneyTotal;

    @ApiModelProperty(value = "可用金额")
    private Integer moneyAvailable;

    @ApiModelProperty(value = "冻结金额")
    private Integer moneyFrozen;

    @ApiModelProperty(value = "历史总积分")
    private Integer pointsTotalHistory;

    @ApiModelProperty(value = "总积分")
    private Integer pointsTotal;

    @ApiModelProperty(value = "可用积分")
    private Integer pointsAvailable;

    @ApiModelProperty(value = "冻结积分")
    private Integer pointsFrozen;

}