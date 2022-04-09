package cn.fufeii.ds.common.enumerate.biz;

import cn.fufeii.ds.common.util.KeyValuePair;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 分润计算方式, 即在原有的某一个基准上
 * 进行百分比后分润, 或者直接按照固定值分润
 *
 * @author Fu Fei
 * @date 2020/7/13
 */
@Getter
@AllArgsConstructor
public enum CalculateModeEnum {

    /**
     * 百分比
     */
    PERCENTAGE(1, "百分比"),

    /**
     * 固定值
     */
    FIXED(2, "固定");

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
