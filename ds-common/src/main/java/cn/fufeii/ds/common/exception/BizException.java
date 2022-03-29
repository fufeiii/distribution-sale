package cn.fufeii.ds.common.exception;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.ResultDefinition;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 业务异常
 *
 * @author FuFei
 * @date 2022/3/13
 */
@Getter
@Setter
@NoArgsConstructor
public class BizException extends RuntimeException {

    private Integer code;

    public BizException(ResultDefinition resultDefinition, String... params) {
        super(resultDefinition.getMsg(params));
        code = resultDefinition.getCode();
    }

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public static BizException serverError(String msg) {
        return new BizException(ExceptionEnum.SERVER_ERROR.getCode(), msg);
    }

    public static BizException serverError(ExceptionEnum exceptionEnum, String... params) {
        return new BizException(exceptionEnum, params);
    }

}
