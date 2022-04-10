package cn.fufeii.ds.server.model.api.response;

import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员树
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Data
@ApiModel
public class MemberTeamResponse {

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

    @ApiModelProperty("身份类型")
    private MemberIdentityTypeEnum identityType;

    @ApiModelProperty("段位类型")
    private MemberRankTypeEnum rankType;

    @ApiModelProperty("状态")
    private StateEnum state;

}
