package cn.fufeii.ds.common.aop;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.constant.DsConstant;
import cn.hutool.core.text.StrPool;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * 全局锁切面
 *
 * @author Fu Fei
 * @date 2022/3/20
 */
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class GlobalLockAspect {

    private final LocalVariableTableParameterNameDiscoverer localVariableTableParameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    private final ExpressionParser expressionParser = new SpelExpressionParser();

    @Autowired
    private RedissonClient redissonClient;

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @Pointcut("@annotation(cn.fufeii.ds.common.annotation.GlobalLock)")
    public void pointcut() {
    }

    /**
     * Redisson全局锁切面
     */
    @Around("pointcut()")
    public Object redisson(ProceedingJoinPoint pjp) throws Throwable {
        // 获取锁名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = pjp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
        GlobalLock globalLock = method.getAnnotation(GlobalLock.class);
        final String glKey = this.getGlKey(globalLock.key(), method, pjp.getArgs());

        RLock rLock = redissonClient.getLock(glKey);
        Logger log = this.logger(pjp);
        boolean isLocked = false;
        try {
            // 加锁
            if (globalLock.tryLock()) {
                isLocked = rLock.tryLock(globalLock.waitTime(), TimeUnit.SECONDS);
            } else {
                rLock.lock();
                isLocked = true;
            }
            // 加锁失败抛出异常
            if (!isLocked) {
                throw new IllegalStateException(StrUtil.format("加锁失败，线程{}：{}", Thread.currentThread().getName(), glKey));
            }
            // 执行目标方法
            log.debug("加锁成功：{}", glKey);
            return pjp.proceed();
        } finally {
            // 加锁成功才解锁
            if (isLocked) {
                rLock.unlock();
                log.debug("解锁成功：{}", glKey);
            }
        }
    }


    /**
     * 获取锁名
     * 这个key可能是显示定义的，或者是springEL表达式，或者没有填写
     *
     * @param rawKey 注解上的关键字
     * @param method 被解析方法
     * @param args   被解析方法的参数
     */
    public String getGlKey(String rawKey, Method method, Object[] args) {
        String keyPrefix = DsConstant.REDIS_NAMESPACE + StrPool.COLON + DsConstant.GL_NAMESPACE + StrPool.COLON;
        // 没填写key
        if (StrUtil.isBlank(rawKey)) {
            return keyPrefix + method.getDeclaringClass().getSimpleName() + StrPool.COLON + method.getName();
        }

        // 不是sp-el表达式直接返回
        String spElFlag = "#";
        if (!rawKey.contains(spElFlag)) {
            return keyPrefix + rawKey;
        }

        // 准备设置sp-el的变量参数
        String[] parameterNames = localVariableTableParameterNameDiscoverer.getParameterNames(method);
        // 参数列表为空
        if (ArrayUtil.isEmpty(parameterNames)) {
            return keyPrefix + rawKey;
        }
        // 进行springEL解析
        StandardEvaluationContext context = new StandardEvaluationContext();
        for (int i = 0; i < parameterNames.length; i++) {
            context.setVariable(parameterNames[i], args[i]);
        }
        return keyPrefix + expressionParser.parseExpression(rawKey).getValue(context, String.class);
    }


}
