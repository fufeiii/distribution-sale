package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 分润事件
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_allot_profit_event")
public class AllotProfitEvent extends BaseEntity {

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
     * 分润类型
     */
    @TableField
    private ProfitTypeEnum profitType;

    /**
     * 事件触发会员主键
     */
    @TableField
    private Long triggerMemberId;

    /**
     * 事件编号
     */
    @TableField
    private String eventNumber;

    /**
     * 事件金额
     */
    @TableField
    private Integer eventAmount;

    /**
     * 通知状态
     */
    @TableField
    private NotifyStateEnum notifyState;

    /**
     * 备注
     */
    @TableField
    private String memo;

}