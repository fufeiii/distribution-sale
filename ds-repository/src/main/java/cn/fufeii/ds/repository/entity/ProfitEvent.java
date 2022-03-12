package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 分润事件
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_profit_event")
public class ProfitEvent {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 分润类型
     */
    @TableField
    private Integer profitType;

    /**
     * 事件触发人
     */
    @TableField
    private String triggerMemberUsername;

    /**
     * 事件编号，交易分润时为商户订单编号
     */
    @TableField
    private String eventNumber;

    /**
     * 事件金额，目前仅交易分润记录订单金额
     */
    @TableField
    private Long eventPrice;

    /**
     * 备注
     */
    @TableField
    private String memo;

    /**
     * 创建事件
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDateTime;

}