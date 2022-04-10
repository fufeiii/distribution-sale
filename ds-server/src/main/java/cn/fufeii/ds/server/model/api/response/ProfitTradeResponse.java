package cn.fufeii.ds.server.model.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * ProfitTradeResponse
 *
 * @author FuFei
 * @date 2022/4/10
 */
@ApiModel
@Data
public class ProfitTradeResponse {

    @ApiModelProperty(value = "事件主键")
    private Long eventId;

}
