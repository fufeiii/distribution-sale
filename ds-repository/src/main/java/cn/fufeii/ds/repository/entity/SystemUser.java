package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_system_user")
public class SystemUser extends BaseEntity {

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
     * 登录名
     */
    @TableField
    private String username;

    /**
     * 用户名
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

}