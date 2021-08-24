package cn.fufeii.ds.common.util;

import lombok.Getter;

/**
 * 公共枚举
 *
 * @author FuFei
 * @date 2021/8/22
 */
@Getter
public enum CommonEnum {

    /**
     * 成功
     */
    SUCCESS(0, "成功");


    private final int code;
    private final String msg;

    CommonEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
