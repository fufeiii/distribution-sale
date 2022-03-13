package cn.fufeii.ds.common.result;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.ResultDefinition;
import lombok.Data;

/**
 * 公共结果
 * 所有restful接口应该使用此类包装真实的数据
 *
 * @author FuFei
 * @date 2021/8/22
 */
@Data
public final class CommonResult<T> {

    private final int code;

    private final String msg;

    private final T data;

    private final boolean success;

    private CommonResult(int code, String msg, T data) {
        this.success = code == ExceptionEnum.SUCCESS.getCode();
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> CommonResult<T> success() {
        return success(ExceptionEnum.SUCCESS, null);
    }

    public static <T> CommonResult<T> success(T data) {
        return success(ExceptionEnum.SUCCESS, data);
    }

    public static <T> CommonResult<T> success(ResultDefinition rd, T data) {
        return new CommonResult<>(rd.getCode(), rd.getMsg(), data);
    }

    public static <T> CommonResult<T> fail(int code, String msg) {
        return new CommonResult<>(code, msg, null);
    }

    public static <T> CommonResult<T> fail(ResultDefinition rd) {
        return fail(rd.getCode(), rd.getMsg());
    }

}
