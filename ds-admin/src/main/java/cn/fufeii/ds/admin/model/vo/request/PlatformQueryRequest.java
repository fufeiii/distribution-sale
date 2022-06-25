package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.model.PageRequest;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 平台信息 Request
 *
 * @author FuFei
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@ApiModel
public class PlatformQueryRequest extends PageRequest {

    @ApiModelProperty(value = "平台标识")
    private String username;

    @ApiModelProperty(value = "平台名称")
    private String nickname;

}