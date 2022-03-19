package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员信息 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class MemberQueryRequest {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "页数")
    private Integer size = 10;

    @ApiModelProperty(value = "用户标识")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

}