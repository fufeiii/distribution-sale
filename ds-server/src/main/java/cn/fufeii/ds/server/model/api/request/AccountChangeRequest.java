package cn.fufeii.ds.server.model.api.request;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.ChangeTypeEnum;
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
public class AccountChangeRequest {

    @NotBlank
    @ApiModelProperty(value = "会员标识", required = true)
    private String memberUsername;

    @NotNull
    @ApiModelProperty(value = "账户类型", required = true)
    private AccountTypeEnum accountType;

    @NotNull
    @ApiModelProperty(value = "变动类型", required = true)
    private ChangeTypeEnum changeType;

    @NotBlank
    @ApiModelProperty(value = "变动业务编号", required = true)
    private String changeBizNumber;

    @NotNull
    @ApiModelProperty(value = "变动数", required = true)
    private Integer changeCount;

    @NotBlank
    @ApiModelProperty(value = "备注", required = true)
    private String memo;

}
