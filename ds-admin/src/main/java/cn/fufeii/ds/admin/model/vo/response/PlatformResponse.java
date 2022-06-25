package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 平台信息 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class PlatformResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "平台标识")
    private String username;

    @ApiModelProperty(value = "平台名称")
    private String nickname;

    @ApiModelProperty(value = "签名密钥")
    private String sk;

    @ApiModelProperty(value = "通知地址")
    private String notifyUrl;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}