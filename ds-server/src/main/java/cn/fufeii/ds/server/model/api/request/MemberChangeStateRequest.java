package cn.fufeii.ds.server.model.api.request;

import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author FuFei
 */
@Data
@ApiModel
public class MemberChangeStateRequest {

    @NotBlank
    @ApiModelProperty(value = "会员标识", required = true)
    private String username;

    @NotNull
    @ApiModelProperty(value = "状态", required = true)
    private StateEnum state;

}
