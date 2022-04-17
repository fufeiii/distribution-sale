package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.*;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分润配置
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_allot_profit_config")
public class AllotProfitConfig extends BaseEntity {

    /**
     * 平台标识
     */
    @TableField
    private String platformUsername;

    /**
     * 平台名称
     */
    @TableField
    private String platformNickname;

    /**
     * 账户类型
     */
    @TableField
    private AccountTypeEnum accountType;

    /**
     * 分润类型
     */
    @TableField
    private ProfitTypeEnum profitType;

    /**
     * 计算方式
     */
    @TableField
    private CalculateModeEnum calculateMode;

    /**
     * 分润等级
     */
    @TableField
    private ProfitLevelEnum profitLevel;

    /**
     * 分润比例
     */
    @TableField
    private Integer profitRatio;

    /**
     * 会员类型
     */
    @TableField
    private MemberIdentityTypeEnum memberIdentityType;

    /**
     * 会员段位
     */
    @TableField
    private MemberRankTypeEnum memberRankType;

    /**
     * 状态
     */
    @TableField
    private StateEnum state;

}