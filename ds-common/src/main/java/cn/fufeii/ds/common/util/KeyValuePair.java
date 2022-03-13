package cn.fufeii.ds.common.util;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 键值对工具类
 *
 * @author FuFei
 * @date 2022/3/13
 */
@Data
@AllArgsConstructor
public final class KeyValuePair<K, V> {

    private K key;
    private V value;

}
