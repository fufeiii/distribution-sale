package cn.fufeii.ds.common.util;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * mp的分页工具类
 *
 * @author FuFei
 */
public final class PageUtil {

    /**
     * 创建分页对象
     */
    public static <T> Page<T> pageAndOrderByIdDesc(Integer page, Integer size) {
        return Page.<T>of(page, size).addOrder(OrderItem.desc("id"));
    }

    /**
     * 创建分页对象
     */
    public static <T> Page<T> page(Integer page, Integer size) {
        return Page.of(page, size);
    }

}
