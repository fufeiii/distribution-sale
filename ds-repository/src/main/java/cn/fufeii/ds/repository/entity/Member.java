package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 会员信息
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_member")
public class Member extends BaseEntity {

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
     * 会员标识
     */
    @TableField
    private String username;

    /**
     * 会员昵称
     */
    @TableField
    private String nickname;

    /**
     * 会员头像
     */
    @TableField
    private String avatar;

    /**
     * 第一级邀请人主键
     */
    @TableField
    private Long firstInviterId;

    /**
     * 第二级邀请人主键
     */
    @TableField
    private Long secondInviterId;

    /**
     * 第三级邀请人主键
     */
    @TableField
    private Long thirdInviterId;

    /**
     * 身份类型
     */
    @TableField
    private MemberIdentityTypeEnum identityType;

    /**
     * 段位类型
     */
    @TableField
    private MemberRankTypeEnum rankType;

    /**
     * 状态
     */
    @TableField
    private StateEnum state;

}