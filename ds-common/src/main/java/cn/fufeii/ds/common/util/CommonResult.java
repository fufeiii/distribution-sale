package cn.fufeii.ds.common.util;

import lombok.Data;

/**
 * CommonResult
 *
 * @author FuFei
 * @date 2021/8/22
 */
@Data
public class CommonResult<T> {

    private final int code;

    private final String msg;

    private final T data;

    private final boolean success;

    private CommonResult(int code, String msg, T data) {
        this.success = code == CommonEnum.SUCCESS.getCode();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return success(CommonEnum.SUCCESS.getCode(), null);
    }

    public static <T> CommonResult<T> success(T data) {
        return success(CommonEnum.SUCCESS.getCode(), data);
    }

    public static <T> CommonResult<T> success(int code, T data) {
        CommonEnum success = CommonEnum.SUCCESS;
        return new CommonResult<>(success.getCode(), success.getMsg(), data);
    }

    public static <T> CommonResult<T> fail(int code, String msg) {
        return new CommonResult<>(code, msg, null);
    }

}
