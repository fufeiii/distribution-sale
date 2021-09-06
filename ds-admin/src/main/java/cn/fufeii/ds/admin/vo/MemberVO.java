package cn.fufeii.ds.admin.vo;

import cn.fufeii.ds.common.result.BasePage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会员信息
 *
 * @author FuFei
 */
@Data
@ApiModel
public class MemberVO extends BasePage {

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
    private Integer identityType;

    @ApiModelProperty(value = "用户段位")
    private Integer rankType;

    @ApiModelProperty(value = "用户状态")
    private Integer state;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}