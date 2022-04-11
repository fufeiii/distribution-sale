package cn.fufeii.ds.server.model.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提现申请响应
 *
 * @author FuFei
 * @date 2022/4/11
 */
@ApiModel
@Data
public class WithdrawApplyResponse {

    @ApiModelProperty(value = "提现申请主键")
    private Long withdrawId;

}
