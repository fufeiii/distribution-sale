package cn.fufeii.ds.common.aop;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.util.ValidationUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author FuFei
 * @date 2022/3/20
 */
@Order(15)
@Aspect
@Component
public class DataValidAspect {

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    /**
     * 切类上的注解
     */
    @Pointcut("@within(cn.fufeii.ds.common.annotation.DataValid)")
    public void pointcut1() {
    }

    /**
     * 切方法上的注解
     */
    @Pointcut("@annotation(cn.fufeii.ds.common.annotation.DataValid)")
    public void pointcut2() {
    }

    /**
     * 切面
     */
    @Around("pointcut1() || pointcut2()")
    public Object validation(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                ValidationUtil.Result result = ValidationUtil.validate(arg);
                if (!result.getOk()) {
                    Logger log = this.logger(joinPoint);
                    if (log.isDebugEnabled()) {
                        log.debug("参数校验失败:{},参数:{}", result.getMsg(), args);
                    }
                    return CommonResult.fail(ExceptionEnum.CLIENT_ERROR.getCode(), result.getMsg());
                }
            }
        }
        return joinPoint.proceed();
    }
}
