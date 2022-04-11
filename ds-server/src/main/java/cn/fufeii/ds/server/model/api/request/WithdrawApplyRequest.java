package cn.fufeii.ds.server.model.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 提现申请请求
 *
 * @author FuFei
 * @date 2022/4/11
 */
@ApiModel
@Data
public class WithdrawApplyRequest {

    @NotBlank
    @ApiModelProperty(value = "会员主键", required = true)
    private String memberUsername;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "提现金额", required = true)
    private Integer withdrawAmount;

    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "提现编号", required = true)
    private String withdrawNumber;

    @Size(max = 64)
    @ApiModelProperty(value = "提现编号")
    private String withdrawDesc;

}
