package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.enumerate.biz.StateEnum;
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
public class RankParamQueryRequest {

    @ApiModelProperty(value = "页码")
    private Integer page = 1;

    @ApiModelProperty(value = "页数")
    private Integer size = 10;

    @ApiModelProperty(value = "状态")
    private StateEnum state;

}