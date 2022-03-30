package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 系统用户 Response
 *
 * @author FuFei
 * @date 2022/3/27
 */
@Data
@ApiModel
public class SystemUserResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "平台标识")
    private String platformUsername;

    @ApiModelProperty(value = "平台名称")
    private String platformNickname;

    @ApiModelProperty(value = "登录名")
    private String username;

    @ApiModelProperty(value = "用户名")
    private String nickname;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "状态")
    private String state;

}
