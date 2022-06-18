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

    public BizException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }

    public BizException(ResultDefinition resultDefinition) {
        this(resultDefinition.getCode(), resultDefinition.getMsg());
    }

    public static BizException server(String msg) {
        return new BizException(ExceptionEnum.SERVER_ERROR.getCode(), msg);
    }

    public static BizException client(String msg) {
        return new BizException(ExceptionEnum.CLIENT_ERROR.getCode(), msg);
    }

    public static BizException admin(String msg) {
        return new BizException(ExceptionEnum.ADMIN_ERROR.getCode(), msg);
    }

}
