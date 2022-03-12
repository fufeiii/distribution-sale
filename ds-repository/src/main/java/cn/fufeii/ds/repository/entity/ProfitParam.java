package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 分润参数
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_profit_param")
public class ProfitParam {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 账户类型
     */
    @TableField
    private Integer accountType;

    /**
     * 分润类型
     */
    @TableField
    private Integer profitType;

    /**
     * 计算方式
     */
    @TableField
    private Integer calculateMode;

    /**
     * 分润等级
     */
    @TableField
    private Integer profitLevel;

    /**
     * 分润比例
     */
    @TableField
    private Integer profitRatio;

    /**
     * 用户类型
     */
    @TableField
    private Integer memberIdentityType;

    /**
     * 用户段位
     */
    @TableField
    private Integer memberRankType;

    /**
     * 状态
     */
    @TableField
    private Integer state;

    /**
     * 乐观锁
     */
    @Version
    @TableField
    private Integer version;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDateTime;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDateTime;

}