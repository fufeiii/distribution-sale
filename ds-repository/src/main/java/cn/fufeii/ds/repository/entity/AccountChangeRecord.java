package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.ChangeTypeEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 账户变动记录
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_account_change_record")
public class AccountChangeRecord extends BaseEntity {

    /**
     * 会员主键
     */
    @TableField
    private Long memberId;

    /**
     * 账户主键
     */
    @TableField
    private Long accountId;

    /**
     * 账户类型
     */
    @TableField
    private AccountTypeEnum accountType;

    /**
     * 变动类型
     */
    @TableField
    private ChangeTypeEnum changeType;

    /**
     * 变动业务编号
     */
    @TableField
    private String changeBizNumber;

    /**
     * 变动前可用总数
     */
    @TableField
    private Integer beforeAvailableTotal;

    /**
     * 变动后可用总数
     */
    @TableField
    private Integer afterAvailableTotal;

    /**
     * 变动数
     */
    @TableField
    private Integer changeCount;

    /**
     * 变动描述
     */
    @TableField
    private String memo;

}