package cn.fufeii.ds.server.model.api.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author FuFei
 */
@Data
@ApiModel
public class AccountChangeRecordResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员主键")
    private Long memberId;

    @ApiModelProperty(value = "账户主键")
    private Long accountId;

    @ApiModelProperty(value = "会员标识")
    private String memberUsername;

    @ApiModelProperty(value = "账户类型")
    private String accountType;

    @ApiModelProperty(value = "变动前总数")
    private Integer beforeAvailableCount;

    @ApiModelProperty(value = "变动后总数")
    private Integer afterAvailableCount;

    @ApiModelProperty(value = "变动数")
    private Integer changeCount;

    @ApiModelProperty(value = "变动类型")
    private String changeType;

    @ApiModelProperty(value = "变动业务编号")
    private String changeBizNumber;

    @ApiModelProperty(value = "备注")
    private String memo;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}