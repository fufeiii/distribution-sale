package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
    private Long memberId;

    /**
     * 总金额
     */
    private Long moneyTotal;

    /**
     * 可用金额
     */
    private Long moneyAvailable;

    /**
     * 冻结金额
     */
    private Long moneyFrozen;

    /**
     * 历史总积分
     */
    private Integer integralTotalHistory;

    /**
     * 总积分
     */
    private Integer integralTotal;

    /**
     * 可用积分
     */
    private Integer integralAvailable;

    /**
     * 冻结积分
     */
    private Integer integralFrozen;

    /**
     * 账户状态
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