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
     * 存入
     */
    INCREASE(2, "增加"),

    /**
     * 取出
     */
    DECREASE(3, "减少"),

    /**
     * 提现冻结
     */
    FREEZE(4, "冻结"),

    /**
     * 提现解冻
     */
    UNFREEZE(5, "解冻");

    @EnumValue
    private final Integer code;
    private final String message;

}
