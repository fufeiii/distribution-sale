package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 分销事件
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
     * 平台主键
     */
    @TableField
    private Long platformId;

    /**
     * 平台名称
     */
    @TableField
    private String platformUsername;

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
     * 备注
     */
    @TableField
    private String memo;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDateTime;

}