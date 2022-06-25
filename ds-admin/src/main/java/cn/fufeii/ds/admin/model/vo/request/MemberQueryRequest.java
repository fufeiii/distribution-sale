package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.model.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 会员信息 Request
 *
 * @author FuFei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@ApiModel
public class MemberQueryRequest extends PageRequest {

    @ApiModelProperty(value = "会员标识")
    private String username;

    @ApiModelProperty(value = "会员名称")
    private String nickname;

}