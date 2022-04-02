package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 系统用户 Response
 *
 * @author FuFei
 * @date 2022/3/27
 */
@Data
@ApiModel
public class SystemUserCreateRequest {

    @NotBlank
    @ApiModelProperty(value = "平台标识")
    private String platformUsername;

    @NotBlank
    @Size(max = 16)
    @ApiModelProperty(value = "登录名")
    private String username;

    @NotBlank
    @Size(max = 16)
    @ApiModelProperty(value = "用户名")
    private String nickname;

    @Size(max = 255)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @NotBlank
    @Size(min = 5, max = 16)
    @ApiModelProperty(value = "密码")
    private String password;

}
