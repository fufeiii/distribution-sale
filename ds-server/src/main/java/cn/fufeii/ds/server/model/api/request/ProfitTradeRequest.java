package cn.fufeii.ds.server.model.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
    @ApiModelProperty(value = "会员主键", required = true)
    private String username;

    @Range(min = 1)
    @NotNull
    @ApiModelProperty(value = "交易金额", required = true)
    private Integer tradeAmount;

    @NotBlank
    @ApiModelProperty(value = "交易编号", required = true)
    private String tradeNumber;

}
