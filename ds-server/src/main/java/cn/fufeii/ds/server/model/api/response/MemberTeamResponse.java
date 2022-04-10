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

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "平台标识")
    private String platformUsername;

    @ApiModelProperty(value = "平台名称")
    private String platformNickname;

    @ApiModelProperty(value = "会员标识")
    private String username;

    @ApiModelProperty(value = "会员名称")
    private String nickname;

    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @ApiModelProperty(value = "身份类型")
    private MemberIdentityTypeEnum identityType;

    @ApiModelProperty(value = "段位类型")
    private MemberRankTypeEnum rankType;

    @ApiModelProperty(value = "状态")
    private StateEnum state;

}
