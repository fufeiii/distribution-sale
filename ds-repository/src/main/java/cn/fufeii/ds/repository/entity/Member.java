package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 会员信息
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_member")
public class Member {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 用户标识
     */
    @TableField
    private String username;

    /**
     * 用户昵称
     */
    @TableField
    private String nickname;

    /**
     * 用户头像
     */
    @TableField
    private String avatar;

    /**
     * 第一层级父级标识
     */
    @TableField
    private String firParent;

    /**
     * 第二层级父级标识
     */
    @TableField
    private String secParent;

    /**
     * 第三层级父级标识
     */
    @TableField
    private String thrParent;

    /**
     * 父级全路径
     */
    @TableField
    private String parentPath;

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
     * 用户状态
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