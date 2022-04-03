package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会员信息 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class MemberResponse {

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

    @ApiModelProperty(value = "第一层级父级标识")
    private String firstInviterUsername;

    @ApiModelProperty(value = "第一层级父级名称")
    private String firstInviterNickname;

    @ApiModelProperty(value = "第二层级父级标识")
    private String secondInviterUsername;

    @ApiModelProperty(value = "第二层级父级名称")
    private String secondInviterNickname;

    @ApiModelProperty(value = "第三层级父级标识")
    private String thirdInviterUsername;

    @ApiModelProperty(value = "第三层级父级名称")
    private String thirdInviterNickname;

    @ApiModelProperty(value = "身份类型")
    private String identityType;

    @ApiModelProperty(value = "段位类型")
    private String rankType;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}