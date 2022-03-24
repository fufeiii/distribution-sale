package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 系统用户
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_sys_user")
public class SysUser {

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
    private String platformUsername;

    /**
     * 登录名
     */
    @TableField
    private String username;

    /**
     * 名称
     */
    @TableField
    private String nickname;

    /**
     * 头像
     */
    @TableField
    private String avatar;

    /**
     * 密码
     */
    @TableField
    private String password;

    /**
     * 密码盐
     */
    @TableField
    private String slat;

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