package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 提现状态
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Getter
@AllArgsConstructor
public enum WithdrawStateEnum {
    /**
     * 待审批
     */
    WAIT(1, "等待审批"),

    /**
     * 审批通过
     */
    PASS(2, "审批通过"),

    /**
     * 审批拒绝
     */
    REJECT(3, "审批拒绝"),

    /**
     * 提现失败
     */
    FAIL(4, "提现失败"),

    /**
     * 提现成功
     */
    SUCCESS(5, "提现成功");

    @EnumValue
    private final Integer code;
    private final String message;

}
