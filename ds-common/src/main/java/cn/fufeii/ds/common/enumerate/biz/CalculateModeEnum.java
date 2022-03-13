package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分润计算方式，即在原有的某一个基准上
 * 进行百分比后分润，或者直接按照固定值分润
 *
 * @author Fu Fei
 * @date 2020/7/13
 */
@Getter
@AllArgsConstructor
public enum CalculateModeEnum {

    /**
     * 百分比
     */
    PERCENTAGE(1, "百分比"),

    /**
     * 固定值
     */
    FIXED(2, "固定");

    @EnumValue
    private final Integer code;
    private final String message;

}
