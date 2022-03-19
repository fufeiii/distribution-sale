package cn.fufeii.ds.admin.model.vo.request;

import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 段位配置 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class RankParamUpsertRequest {

    @ApiModelProperty(value = "主键（修改是必传）")
    private Long id;

    @ApiModelProperty(value = "会员段位类型")
    private MemberRankTypeEnum memberRankType;

    @ApiModelProperty(value = "开始积分")
    private Integer beginIntegral;

    @ApiModelProperty(value = "结束积分")
    private Integer endIntegral;

}