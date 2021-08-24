package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * 段位配置
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_rank_param")
public class RankParam {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户段位
     */
    private Integer memberRank;

    /**
     * 开始积分
     */
    private Integer beginIntegral;

    /**
     * 结束积分
     */
    private Integer endIntegral;

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