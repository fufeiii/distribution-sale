package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分销等级枚举
 * 将作为分润的参数
 *
 * @author Fu Fei
 * @date 2020/7/18
 */
@Getter
@AllArgsConstructor
public enum ProfitLevelEnum {

    /**
     * 自身
     */
    ZERO(1, "自身"),

    /**
     * 一级
     */
    ONE(2, "一级"),

    /**
     * 二级
     */
    TWO(3, "二级"),

    /**
     * 三级
     */
    THREE(4, "三级");

    @EnumValue
    private final Integer code;
    private final String message;

}
