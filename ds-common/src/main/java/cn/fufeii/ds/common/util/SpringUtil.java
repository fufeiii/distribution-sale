package cn.fufeii.ds.common.util;

import cn.hutool.core.util.ArrayUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * SpringIOC 容器工具类
 * 使用@Import(SpringUtil.class)导入或者在此类加上@Component
 *
 * @author FuFei
 */
@Component
public final class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private SpringUtil() {
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext context) {
        applicationContext = context;
    }

    /**
     * 通过name获取Bean
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        return (T) applicationContext.getBean(name);
    }

    /**
     * 通过class获取Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过name，以及Clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return applicationContext.getBean(name, clazz);
    }

    /**
     * 获取指定类型对应的所有Bean，包括子类
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> type) {
        return applicationContext.getBeansOfType(type);
    }

    /**
     * 获取指定类型对应的Bean名称，包括子类
     */
    public static String[] getBeanNamesForType(Class<?> type) {
        return applicationContext.getBeanNamesForType(type);
    }

    /**
     * 获取配置文件配置项的值
     */
    public static String getProperty(String key) {
        return applicationContext.getEnvironment().getProperty(key);
    }

    /**
     * 获取当前的环境配置，无配置返回null
     */
    public static String[] getActiveProfiles() {
        return applicationContext.getEnvironment().getActiveProfiles();
    }

    /**
     * 获取当前的环境配置，当有多个环境配置时，只获取第一个
     */
    public static String getActiveProfile() {
        final String[] activeProfiles = getActiveProfiles();
        return ArrayUtil.isNotEmpty(activeProfiles) ? activeProfiles[0] : null;
    }

}