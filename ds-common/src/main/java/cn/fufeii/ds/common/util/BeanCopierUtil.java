package cn.fufeii.ds.common.util;


import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.springframework.cglib.beans.BeanCopier;

import java.util.ArrayList;
import java.util.List;

/**
 * 利用CGLIB的BeanCopier类
 * 实现高性能的对象转换
 *
 * @author FuFei
 */
public final class BeanCopierUtil {

    /**
     * 缓存BeanCopier
     */
    private static final Table<String, String, BeanCopier> BEAN_COPIER_TABLE = HashBasedTable.create();

    private BeanCopierUtil() {
    }

    public static <T> T copy(Object source, Class<T> targetClass) {
        T target = getTarget(targetClass);
        copy(source, target);
        return target;
    }

    public static void copy(Object source, Object target) {
        getBeanCopier(source.getClass(), target.getClass()).copy(source, target, null);
    }

    public static <S, T> List<T> copy(List<S> sourceList, Class<T> targetClass) {
        BeanCopier beanCopier = getBeanCopier(sourceList.get(0).getClass(), targetClass);
        List<T> targetList = new ArrayList<>(sourceList.size());
        for (S s : sourceList) {
            T target = getTarget(targetClass);
            beanCopier.copy(s, target, null);
            targetList.add(target);
        }
        return targetList;
    }

    private static <S, T> BeanCopier getBeanCopier(Class<S> sourceClass, Class<T> targetClass) {
        BeanCopier beanCopier = BEAN_COPIER_TABLE.get(sourceClass.getName(), targetClass.getName());
        if (beanCopier == null) {
            beanCopier = BeanCopier.create(sourceClass, targetClass, false);
            BEAN_COPIER_TABLE.put(sourceClass.getName(), targetClass.getName(), beanCopier);
        }
        return beanCopier;
    }

    private static <T> T getTarget(Class<T> targetClass) {
        try {
            return targetClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}