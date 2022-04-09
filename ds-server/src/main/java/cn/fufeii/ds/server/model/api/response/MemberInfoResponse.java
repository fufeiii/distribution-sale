package cn.fufeii.ds.server.model.api.response;

import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员信息
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Data
@ApiModel
public class MemberInfoResponse {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("平台标识")
    private String platformUsername;

    @ApiModelProperty("平台名称")
    private String platformNickname;

    @ApiModelProperty("会员标识")
    private String username;

    @ApiModelProperty("会员名称")
    private String nickname;

    @ApiModelProperty("会员头像")
    private String avatar;

    @ApiModelProperty("第一邀请人主键")
    private Long firstInviterId;

    @ApiModelProperty("第一邀请人标识")
    private String firstInviterUsername;

    @ApiModelProperty("第二邀请人主键")
    private Long secondInviterId;

    @ApiModelProperty("第二邀请人标识")
    private String secondInviterUsername;

    @ApiModelProperty("第三邀请人主键")
    private Long thirdInviterId;

    @ApiModelProperty("第三邀请人标识")
    private String thirdInviterUsername;

    @ApiModelProperty("身份类型")
    private MemberIdentityTypeEnum identityType;

    @ApiModelProperty("段位类型")
    private MemberRankTypeEnum rankType;

    @ApiModelProperty("段位类型")
    private StateEnum state;

    @ApiModelProperty("历史总金额")
    private Integer moneyTotalHistory;

    @ApiModelProperty("总金额")
    private Integer moneyTotal;

    @ApiModelProperty("可用金额")
    private Integer moneyAvailable;

    @ApiModelProperty("冻结金额")
    private Integer moneyFrozen;

    @ApiModelProperty("历史总积分")
    private Integer pointsTotalHistory;

    @ApiModelProperty("总积分")
    private Integer pointsTotal;

    @ApiModelProperty("可用积分")
    private Integer pointsAvailable;

    @ApiModelProperty("冻结积分")
    private Integer pointsFrozen;

}