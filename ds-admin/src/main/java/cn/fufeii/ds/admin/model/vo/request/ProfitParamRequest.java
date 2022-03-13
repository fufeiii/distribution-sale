package cn.fufeii.ds.admin.model.vo.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分润参数 Request
 *
 * @author FuFei
 */
@Data
@ApiModel
public class ProfitParamRequest {

    /**
     * 页码
     */
    private Integer page;

    /**
     * 页数
     */
    private Integer size;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 账户类型
     */
    @ApiModelProperty(value = "账户类型")
    private Integer accountType;

    /**
     * 分润类型
     */
    @ApiModelProperty(value = "分润类型")
    private Integer profitType;

    /**
     * 计算方式
     */
    @ApiModelProperty(value = "计算方式")
    private Integer calculateMode;

    /**
     * 分润等级
     */
    @ApiModelProperty(value = "分润等级")
    private Integer profitLevel;

    /**
     * 分润比例
     */
    @ApiModelProperty(value = "分润比例")
    private Integer profitRatio;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
    private Integer memberIdentityType;

    /**
     * 用户段位
     */
    @ApiModelProperty(value = "用户段位")
    private Integer memberRankType;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer state;

    /**
     * 乐观锁
     */
    @ApiModelProperty(value = "乐观锁")
    private Integer version;

    /**
     * 更新时间
     */
    @ApiModelProperty(value = "更新时间")
    private Date updateDateTime;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createDateTime;

}