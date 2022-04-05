package cn.fufeii.ds.common.annotation;

import java.lang.annotation.*;

/**
 * 全局锁
 *
 * @author FuFei
 * @date 2022/3/20
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface GlobalLock {

    /**
     * 锁名
     * 支持sp-el表达式
     * 不填写此值，默认值为被注解的方法所在类的 类名:方法名
     */
    String key() default "";

    /**
     * 是否尝试加锁
     * 注意：底层都是使用的tryLock(long time, TimeUnit unit)，
     * 若为false，则time为0
     * 若为true则最多等待{@link #waitTime()}秒
     */
    boolean tryLock() default true;

    /**
     * 等待时间
     */
    long waitTime() default 10;

}
