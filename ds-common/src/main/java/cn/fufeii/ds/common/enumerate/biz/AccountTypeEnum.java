package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户类型。
 * 佣金或者积分
 *
 * @author Fu Fei
 * @date 2020/8/8
 */
@Getter
@AllArgsConstructor
public enum AccountTypeEnum {

    /**
     * 佣金
     */
    MONEY(1, "佣金"),

    /**
     * 积分
     */
    INTEGRAL(2, "积分");

    @EnumValue
    private final Integer code;
    private final String message;

}
