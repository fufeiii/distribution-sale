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

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty("平台标识")
    private String username;

    @ApiModelProperty("平台名称")
    private String nickname;

    @ApiModelProperty("签名密钥")
    private String sk;

    @ApiModelProperty("通知地址")
    private String notifyUrl;

    @ApiModelProperty("状态")
    private String state;

    @ApiModelProperty("创建时间")
    private Date createDateTime;

}