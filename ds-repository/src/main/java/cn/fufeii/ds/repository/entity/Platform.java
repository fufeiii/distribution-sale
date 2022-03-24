package cn.fufeii.ds.repository.entity;

import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

/**
 * 平台
 *
 * @author FuFei
 */
@Data
@TableName(value = "ds_platform")
public class Platform {

    /**
     * 主键
     */
    @TableId
    private Long id;

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
     * 平台的webhook
     */
    @TableField
    private String webhook;

    /**
     * 签名密钥
     */
    @TableField
    private String sk;

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