package cn.fufeii.ds.repository.entity;

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
    private Long memberId;

    /**
     * 账户主键
     */
    private Long accountId;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 变动前总数
     */
    private Long beforeChangeTotal;

    /**
     * 变动后总数
     */
    private Long afterChangeTotal;

    /**
     * 变动数
     */
    private Long changeAmount;

    /**
     * 变动类型
     */
    private Integer changeType;

    /**
     * 变动记录主键
     */
    private Long changeRecordId;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createTime;

}