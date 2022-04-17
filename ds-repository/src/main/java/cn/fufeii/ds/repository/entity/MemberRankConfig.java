package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员段位配置
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_member_rank_config")
public class MemberRankConfig extends BaseEntity {

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

}