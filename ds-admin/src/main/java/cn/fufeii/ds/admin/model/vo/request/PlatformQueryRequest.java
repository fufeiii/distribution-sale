package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 平台信息 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class PlatformQueryRequest {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "页数")
    private Integer size = 10;

    @ApiModelProperty(value = "平台标识")
    private String username;

    @ApiModelProperty(value = "平台名称")
    private String nickname;

}