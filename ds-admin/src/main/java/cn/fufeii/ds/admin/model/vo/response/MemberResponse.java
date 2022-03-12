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

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户标识
     */
    @ApiModelProperty(value = "用户标识")
    private String username;

    /**
     * 用户昵称
     */
    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    /**
     * 用户头像
     */
    @ApiModelProperty(value = "用户头像")
    private String avatar;

    /**
     * 第一层级父级标识
     */
    @ApiModelProperty(value = "第一层级父级标识")
    private String firParent;

    /**
     * 第二层级父级标识
     */
    @ApiModelProperty(value = "第二层级父级标识")
    private String secParent;

    /**
     * 第三层级父级标识
     */
    @ApiModelProperty(value = "第三层级父级标识")
    private String thrParent;

    /**
     * 父级全路径
     */
    @ApiModelProperty(value = "父级全路径")
    private String parentPath;

    /**
     * 身份类型
     */
    @ApiModelProperty(value = "身份类型")
    private Integer identityType;

    /**
     * 段位类型
     */
    @ApiModelProperty(value = "段位类型")
    private Integer rankType;

    /**
     * 用户状态
     */
    @ApiModelProperty(value = "用户状态")
    private Integer state;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String memo;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateDateTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}