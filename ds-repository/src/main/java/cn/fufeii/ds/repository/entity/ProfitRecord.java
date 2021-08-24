package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 分润记录
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
    private Long eventId;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 获利会员主键
     */
    private Long impactMemberId;

    /**
     * 获利数
     */
    private Long incomeAmount;

    /**
     * 备注
     */
    private String memo;

    /**
     * 创建时间
     */
    private Date createTime;

}