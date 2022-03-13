package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 通用状态
 *
 * @author Fu Fei
 * @date 2020/7/8
 */
@Getter
@AllArgsConstructor
public enum StateEnum {

    /**
     * 禁用状态
     */
    DISABLES(0, "禁用"),

    /**
     * 开启状态
     */
    ENABLE(1, "启用");

    @EnumValue
    private final Integer code;
    private final String message;

}
