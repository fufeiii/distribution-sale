package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 会员账户
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_account")
public class Account {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 会员主键
     */
    @TableField
    private Long memberId;

    /**
     * 总金额
     */
    @TableField
    private Long moneyTotal;

    /**
     * 可用金额
     */
    @TableField
    private Long moneyAvailable;

    /**
     * 冻结金额
     */
    @TableField
    private Long moneyFrozen;

    /**
     * 历史总积分
     */
    @TableField
    private Integer integralTotalHistory;

    /**
     * 总积分
     */
    @TableField
    private Integer integralTotal;

    /**
     * 可用积分
     */
    @TableField
    private Integer integralAvailable;

    /**
     * 冻结积分
     */
    @TableField
    private Integer integralFrozen;

    /**
     * 账户状态
     */
    @TableField
    private Integer state;

    /**
     * 备注
     */
    @TableField
    private String memo;

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