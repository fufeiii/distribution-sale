package cn.fufeii.ds.admin.model.vo.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 段位配置 Response
 *
 * @author FuFei
 */
@Data
@ApiModel
public class RankParamResponse {

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "用户段位类型")
    private String memberRankType;

    @ApiModelProperty(value = "开始积分")
    private Integer beginIntegral;

    @ApiModelProperty(value = "结束积分")
    private Integer endIntegral;

    @ApiModelProperty(value = "状态")
    private String state;

    @ApiModelProperty(value = "更新时间")
    private Date updateDateTime;

    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}