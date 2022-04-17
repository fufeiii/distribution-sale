package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 等级参数 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class MemberRankConfigQueryRequest {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "页数")
    private Integer size = 10;

}