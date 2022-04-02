package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 平台信息 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class PlatformUpsertRequest {

    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "平台标识")
    private String username;

    @NotBlank
    @Size(max = 16)
    @ApiModelProperty(value = "平台名称")
    private String nickname;

    @Size(max = 255)
    @ApiModelProperty(value = "webhook地址")
    private String webhook;

}