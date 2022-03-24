package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 分销记录
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_profit_record")
public class ProfitRecord {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 事件主键
     */
    @TableField
    private Long profitEventId;

    /**
     * 账户类型
     */
    @TableField
    private AccountTypeEnum accountType;

    /**
     * 获利会员主键
     */
    @TableField
    private Long impactMemberId;

    /**
     * 获利数
     */
    @TableField
    private Integer incomeCount;

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