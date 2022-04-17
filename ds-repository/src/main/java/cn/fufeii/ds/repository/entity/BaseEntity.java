package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;

import java.util.Date;

/**
 * 基础实体类
 *
 * @author FuFei
 */
@Data
public abstract class BaseEntity {

    /**
     * 主键
     */
    @TableId
    private Long id;

    /**
     * 乐观锁
     */
    @Version
    @TableField(fill = FieldFill.INSERT)
    private Integer version;

    /**
     * 创建日期时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createDateTime;

    /**
     * 更新日期时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateDateTime;

}
