package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
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
     * 平台主键
     */
    @TableField
    private Long platformId;

    /**
     * 平台名称
     */
    @TableField
    private String platformName;

    /**
     * 会员段位类型
     */
    @TableField
    private MemberRankTypeEnum memberRankType;

    /**
     * 开始积分
     */
    @TableField
    private Integer beginIntegral;

    /**
     * 结束积分
     */
    @TableField
    private Integer endIntegral;

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