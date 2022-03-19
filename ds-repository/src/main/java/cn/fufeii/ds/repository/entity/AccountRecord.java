package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.ChangeTypeEnum;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 账户记录
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_account_record")
public class AccountRecord {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户主键
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
     * 变动前总数
     */
    @TableField
    private Integer beforeChangeTotal;

    /**
     * 变动后总数
     */
    @TableField
    private Integer afterChangeTotal;

    /**
     * 变动数
     */
    @TableField
    private Integer changeAmount;

    /**
     * 变动类型
     */
    @TableField
    private ChangeTypeEnum changeType;

    /**
     * 变动记录主键
     */
    @TableField
    private Long changeRecordId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDateTime;

}