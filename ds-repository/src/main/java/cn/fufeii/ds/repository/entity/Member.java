package cn.fufeii.ds.repository.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
    private String username;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 第一层级父级标识
     */
    private String firParent;

    /**
     * 第二层级父级标识
     */
    private String secParent;

    /**
     * 第三层级父级标识
     */
    private String thrParent;

    /**
     * 父级全路径
     */
    private String parentPath;

    /**
     * 身份类型
     */
    private Integer identityType;

    /**
     * 用户段位
     */
    private Integer rankType;

    /**
     * 用户状态
     */
    private Integer state;

    /**
     * 备注
     */
    private String memo;

    /**
     * 乐观锁
     */
    @Version
    private Integer version;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建时间
     */
    private Date createTime;

}