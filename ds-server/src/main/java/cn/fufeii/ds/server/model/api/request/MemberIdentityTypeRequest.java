package cn.fufeii.ds.server.model.api.request;

import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import com.sun.istack.internal.NotNull;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 会员身份请求
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Data
@ApiModel
public class MemberIdentityTypeRequest {

    @NotBlank
    @ApiModelProperty(value = "会员标识", required = true)
    private String username;

    @NotNull
    @ApiModelProperty(value = "会员身份", required = true)
    private MemberIdentityTypeEnum identityType;

}
