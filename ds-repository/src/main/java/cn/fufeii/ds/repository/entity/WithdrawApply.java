package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.WithdrawStateEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 提现申请
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_withdraw_apply")
public class WithdrawApply {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 平台主键
     */
    @TableField
    private Long platformId;

    /**
     * 平台名称
     */
    @TableField
    private String platformName;

    /**
     * 会员主键
     */
    @TableField
    private Long memberId;

    /**
     * 会员名称
     */
    @TableField
    private String memberName;

    /**
     * 提现单号
     */
    @TableField
    private String withdrawNumber;

    /**
     * 提现金额
     */
    @TableField
    private Integer withdrawAmount;

    /**
     * 手续费
     */
    @TableField
    private Integer feeAmount;

    /**
     * 状态
     */
    @TableField
    private WithdrawStateEnum withdrawState;

    /**
     * 提现描述
     */
    @TableField
    private String withdrawDesc;

    /**
     * 审批时间
     */
    @TableField
    private Date approvalTime;

    /**
     * 审批描述
     */
    @TableField
    private String approvalDesc;

    /**
     * 乐观锁
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
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