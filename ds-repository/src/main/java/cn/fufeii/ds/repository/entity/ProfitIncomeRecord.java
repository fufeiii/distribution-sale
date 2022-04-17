package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 利润收益记录
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_profit_income_record")
public class ProfitIncomeRecord extends BaseEntity {

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

}