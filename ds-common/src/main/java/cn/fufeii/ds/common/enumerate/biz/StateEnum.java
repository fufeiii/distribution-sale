package cn.fufeii.ds.common.enumerate.biz;

import cn.fufeii.ds.common.util.KeyValuePair;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 通用状态
 *
 * @author Fu Fei
 * @date 2020/7/8
 */
@Getter
@AllArgsConstructor
public enum StateEnum {

    /**
     * 开启状态
     */
    ENABLE(1, "启用"),

    /**
     * 禁用状态
     */
    DISABLE(2, "禁用");

    @EnumValue
    private final Integer code;
    private final String message;

    /**
     * 获取枚举类的键值对表达形式集合
     */
    public static List<KeyValuePair<String, String>> getKeyValuePairList() {
        return Arrays.stream(values()).map(it -> new KeyValuePair<>(it.name(), it.getMessage())).collect(Collectors.toList());
    }

}
