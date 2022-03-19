package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提现申请 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class WithdrawApplyQueryRequest {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "页数")
    private Integer size = 10;

    @ApiModelProperty(value = "用户主键")
    private Long memberId;

    @ApiModelProperty(value = "提现单号")
    private String withdrawNumber;

}