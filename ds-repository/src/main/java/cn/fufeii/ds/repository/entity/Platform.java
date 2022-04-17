package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 平台信息
 *
 * @author FuFei
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "ds_platform")
public class Platform extends BaseEntity {

    /**
     * 平台标识
     */
    @TableField
    private String username;

    /**
     * 平台名称
     */
    @TableField
    private String nickname;

    /**
     * 签名密钥
     */
    @TableField
    private String sk;

    /**
     * 回调地址
     */
    @TableField
    private String notifyUrl;

    /**
     * 状态
     */
    @TableField
    private StateEnum state;

}