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

    @ApiModelProperty(value = "用户标识")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "第一层级父级标识")
    private String firParent;

    @ApiModelProperty(value = "第二层级父级标识")
    private String secParent;

    @ApiModelProperty(value = "第三层级父级标识")
    private String thrParent;

    @ApiModelProperty(value = "父级全路径")
    private String parentPath;

    @ApiModelProperty(value = "身份类型")
    private String identityType;

    @ApiModelProperty(value = "段位类型")
    private String rankType;

    @ApiModelProperty(value = "用户状态")
    private String state;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}