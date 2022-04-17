package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员账户
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_account")
public class Account extends BaseEntity {

    /**
     * 会员主键
     */
    @TableField
    private Long memberId;

    /**
     * 历史总金额
     */
    @TableField
    private Integer moneyTotalHistory;

    /**
     * 总金额
     */
    @TableField
    private Integer moneyTotal;

    /**
     * 可用金额
     */
    @TableField
    private Integer moneyAvailable;

    /**
     * 冻结金额
     */
    @TableField
    private Integer moneyFrozen;

    /**
     * 历史总积分
     */
    @TableField
    private Integer pointsTotalHistory;

    /**
     * 总积分
     */
    @TableField
    private Integer pointsTotal;

    /**
     * 可用积分
     */
    @TableField
    private Integer pointsAvailable;

    /**
     * 冻结积分
     */
    @TableField
    private Integer pointsFrozen;

}