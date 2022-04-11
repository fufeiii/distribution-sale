package cn.fufeii.ds.server.model.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 金钱交易分润请求
 *
 * @author FuFei
 * @date 2022/4/10
 */
@ApiModel
@Data
public class ProfitTradeRequest {

    @NotBlank
    @ApiModelProperty(value = "会员标识", required = true)
    private String username;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "交易金额", required = true)
    private Integer tradeAmount;

    @NotBlank
    @Size(max = 32)
    @ApiModelProperty(value = "交易编号", required = true)
    private String tradeNumber;

}
