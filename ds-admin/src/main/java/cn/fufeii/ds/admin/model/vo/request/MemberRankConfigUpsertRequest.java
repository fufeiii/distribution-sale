package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 段位配置 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class MemberRankConfigUpsertRequest {

    @ApiModelProperty(value = "主键（修改是必传）")
    private Long id;

    @ApiModelProperty(value = "会员段位类型")
    private MemberRankTypeEnum memberRankType;

    @NotNull
    @Min(1)
    @ApiModelProperty(value = "开始积分")
    private Integer beginPoints;

    @NotNull
    @Min(2)
    @ApiModelProperty(value = "结束积分")
    private Integer endPoints;

}