package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 账户记录 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class AccountRecordResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "会员主键")
    private Long memberId;

    @ApiModelProperty(value = "账户主键")
    private Long accountId;

    @ApiModelProperty(value = "账户类型")
    private String accountType;

    @ApiModelProperty(value = "变动前总数")
    private Integer beforeChangeTotal;

    @ApiModelProperty(value = "变动后总数")
    private Integer afterChangeTotal;

    @ApiModelProperty(value = "变动数")
    private Integer changeAmount;

    @ApiModelProperty(value = "变动类型")
    private String changeType;

    @ApiModelProperty(value = "变动记录主键")
    private Long changeRecordId;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}