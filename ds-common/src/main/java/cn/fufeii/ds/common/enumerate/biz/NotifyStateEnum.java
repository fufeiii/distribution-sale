package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * NotifyStateEnum
 *
 * @author FuFei
 * @date 2022/4/17
 */
@Getter
@AllArgsConstructor
public enum NotifyStateEnum {

    INIT(1, "初始化"),
    SUCCESS(2, "成功"),
    FAIL(3, "失败");

    @EnumValue
    private final Integer code;
    private final String msg;

}
