package cn.fufeii.ds.common.annotation;

import java.lang.annotation.*;

/**
 * 数据校验
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface DataValid {
}
