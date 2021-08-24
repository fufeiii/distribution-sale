package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
     * 审批时间
     */
    private Date approvalTime;

    /**
     * 用户主键
     */
    private Long memberId;

    /**
     * 提现单号
     */
    private String withdrawNumber;

    /**
     * 提现金额
     */
    private Long withdrawAmount;

    /**
     * 手续费
     */
    private Long feeAmount;

    /**
     * 状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String memo;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}