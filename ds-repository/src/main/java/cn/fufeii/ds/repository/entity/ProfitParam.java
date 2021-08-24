package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * 分润参数
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_profit_param")
public class ProfitParam {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 账户类型
     */
    private Integer accountType;

    /**
     * 分润类型
     */
    private Integer profitType;

    /**
     * 计算方式
     */
    private Integer calculateMode;

    /**
     * 分润等级
     */
    private Integer profitLevel;

    /**
     * 分润比例
     */
    private Integer profitRatio;

    /**
     * 用户类型
     */
    private Integer memberType;

    /**
     * 用户段位
     */
    private Integer memberRank;

    /**
     * 状态
     */
    private Integer state;

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