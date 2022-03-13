package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 提现处置状态枚举
 *
 * @author Fu Fei
 * @date 2020/8/8
 */
@Getter
@AllArgsConstructor
public enum DisposeStateEnum {

    /**
     * 审批中
     */
    WAIT_AUDIT(1, "待审核"),

    /**
     * 待打款
     */
    WAIT_REMIT(2, "待打款"),

    /**
     * 已打款
     */
    DONE_REMIT(3, "已打款"),

    /**
     * 拒绝
     */
    REJECT(4, "已拒绝");

    @EnumValue
    private final Integer code;
    private final String message;

}
