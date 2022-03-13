package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 用户等级标志，与积分相关
 *
 * @author Fu Fei
 * @date 2020/7/12
 */
@Getter
@AllArgsConstructor
public enum MemberRankTypeEnum {

    /**
     * 青铜
     */
    BRONZE(1, "青铜"),

    /**
     * 白银
     */
    SILVER(2, "白银"),

    /**
     * 黄金
     */
    GOLD(3, "黄金"),

    /**
     * 铂金
     */
    PLATINUM(4, "铂金"),

    /**
     * 钻石
     */
    DIAMOND(5, "钻石");

    @EnumValue
    private final Integer code;
    private final String message;

}
