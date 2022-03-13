package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 账户变动表中的变动类型枚举
 *
 * @author Fu Fei
 * @date 2020/7/31
 */
@Getter
@AllArgsConstructor
public enum ChangeTypeEnum {

    /**
     * 分润获利
     */
    PROFIT(1, "分润获利"),

    /**
     * 提现取出
     */
    WITHDRAW_DEPOSIT(2, "提现取出"),

    /**
     * 提现冻结
     */
    WITHDRAW_FREEZE(3, "提现冻结"),

    /**
     * 提现冻结
     */
    WITHDRAW_UNFREEZE(4, "提现冻结");

    @EnumValue
    private final Integer code;
    private final String message;

}
