package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.*;
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
     * 平台标识
     */
    @TableField
    private String platformUsername;

    /**
     * 平台名称
     */
    @TableField
    private String platformNickname;

    /**
     * 会员段位类型
     */
    @TableField
    private MemberRankTypeEnum memberRankType;

    /**
     * 开始积分
     */
    @TableField
    private Integer beginPoints;

    /**
     * 结束积分
     */
    @TableField
    private Integer endPoints;

    /**
     * 状态
     */
    @TableField
    private StateEnum state;

    /**
     * 乐观锁
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
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