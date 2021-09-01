package cn.fufeii.ds.common.web.exception;

import cn.fufeii.ds.common.result.CommonResult;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 * 全局异常处理
 *
 * @author FuFei
 * @date 2021/8/22
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_CLIENT_TPL = "客户端请求异常：{}";

    /**
     * HttpMessageConverter转换异常
     * 一般为JSON解析异常
     */
    @ExceptionHandler({
            HttpMessageNotReadableException.class,
            HttpMediaTypeNotSupportedException.class,
            HttpRequestMethodNotSupportedException.class,
            MissingServletRequestParameterException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public CommonResult<Void> httpMessageNotReadable(Exception exception) {
        String reason;

        if (exception instanceof MissingServletRequestParameterException) {
            // 参数异常特殊处理一下返回提示
            MissingServletRequestParameterException msrpException = (MissingServletRequestParameterException) exception;
            reason = StrUtil.format("缺少参数[{}]，类型[{}]", msrpException.getParameterName(), msrpException.getParameterType());
        } else {
            reason = exception.getMessage();
        }
        log.warn(LOG_CLIENT_TPL, reason);
        return CommonResult.fail(HttpStatus.BAD_REQUEST.value(), reason);
    }


    /**
     * 404找不到资源
     * 需要设置以下参数，设置后swagger会失效，
     * spring.mvc.throw-exception-if-no-handler-found=true
     * spring.resources.add-mappings=false
     * 若不设置也想处理404情况，需要重写{@link org.springframework.boot.web.servlet.error.ErrorAttributes#getErrorAttributes}方法
     *
     * @see org.springframework.boot.web.servlet.error.DefaultErrorAttributes
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public CommonResult<Void> notFound(NoHandlerFoundException exception) {
        String reason = exception.getMessage();
        log.warn(LOG_CLIENT_TPL, reason);
        return CommonResult.fail(HttpStatus.BAD_REQUEST.value(), reason);
    }

    /**
     * json请求参数校验失败
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public CommonResult<Void> methodArgumentNotValidException(MethodArgumentNotValidException exception) {
        BindingResult bindingResult = exception.getBindingResult();
        FieldError fieldError = bindingResult.getFieldError();
        String reason = "";
        if (fieldError != null) {
            reason = "字段[" + fieldError.getField() + "]：" + fieldError.getDefaultMessage();
        }
        log.warn(LOG_CLIENT_TPL, reason);
        return CommonResult.fail(HttpStatus.BAD_REQUEST.value(), reason);
    }


    /**
     * 其他异常
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResult<Void> serverError(Throwable e) {
        log.error("内部异常", e);
        return CommonResult.fail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }


}
