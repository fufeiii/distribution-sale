package cn.fufeii.ds.portal.model.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 创建会员
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Data
@ApiModel
public class MemberCreateRequest {

    @Size(max = 32)
    @NotBlank
    @ApiModelProperty(value = "会员唯一标识", required = true)
    private String username;

    @Size(max = 16)
    @ApiModelProperty(value = "会员昵称")
    private String nickname;

    @Size(max = 16)
    @ApiModelProperty(value = "会员头像")
    private String avatar;

    @Size(max = 32)
    @ApiModelProperty(value = "邀请人会员唯一标识")
    private String inviteUsername;

}
