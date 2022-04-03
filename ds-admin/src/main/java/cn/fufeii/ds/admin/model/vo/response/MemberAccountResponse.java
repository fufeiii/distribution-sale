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
public class MemberAccountResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员主键")
    private Long memberId;

    @ApiModelProperty(value = "会员标识")
    private String memberUsername;

    @ApiModelProperty(value = "会员名称")
    private String memberNickname;

    @ApiModelProperty(value = "会员状态")
    private String memberState;

    @ApiModelProperty(value = "历史总金额")
    private String moneyTotalHistory;

    @ApiModelProperty(value = "总金额")
    private String moneyTotal;

    @ApiModelProperty(value = "可用金额")
    private String moneyAvailable;

    @ApiModelProperty(value = "冻结金额")
    private String moneyFrozen;

    @ApiModelProperty(value = "历史总积分")
    private String pointsTotalHistory;

    @ApiModelProperty(value = "总积分")
    private String pointsTotal;

    @ApiModelProperty(value = "可用积分")
    private String pointsAvailable;

    @ApiModelProperty(value = "冻结积分")
    private String pointsFrozen;

}