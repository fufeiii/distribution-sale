package cn.fufeii.ds.common.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常公共枚举
 *
 * @author FuFei
 * @date 2021/8/22
 */
@Getter
@AllArgsConstructor
public enum ExceptionEnum implements ResultDefinition {

    /**
     * 成功
     */
    SUCCESS(0, "成功");


    private final int code;
    private final String msg;

    public static boolean isSuccess(int code) {
        return SUCCESS.code == code;
    }

}
