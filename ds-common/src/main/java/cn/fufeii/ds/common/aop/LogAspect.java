package cn.fufeii.ds.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * 日志切面
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Order(10)
@Aspect
@Component
public class LogAspect {

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @Pointcut("within(cn.fufeii..*.controller..*)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = this.logger(joinPoint);
        boolean infoEnabled = log.isInfoEnabled();
        if (infoEnabled) {
            log.info("函数{}()入参 = {}", joinPoint.getSignature().getName(), Arrays.toString(joinPoint.getArgs()));
        }
        try {
            long start = System.nanoTime();
            Object result = joinPoint.proceed();
            if (infoEnabled) {
                log.info("函数{}()耗时{}ms出参 = {}", joinPoint.getSignature().getName(), (System.nanoTime() - start) / 100_0000L, result);
            }
            return result;
        } catch (Exception e) {
            log.warn("函数{}()异常={}({})", joinPoint.getSignature().getName(), e.getClass().getSimpleName(), e.getMessage());
            throw e;
        }
    }

}
