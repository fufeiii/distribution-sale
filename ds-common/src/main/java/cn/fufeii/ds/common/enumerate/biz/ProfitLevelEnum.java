package cn.fufeii.ds.common.enumerate.biz;

import cn.fufeii.ds.common.util.KeyValuePair;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 分润等级枚举
 * 将作为分润的参数
 *
 * @author Fu Fei
 * @date 2020/7/18
 */
@Getter
@AllArgsConstructor
public enum ProfitLevelEnum {

    /**
     * 自身
     */
    SELF(1, "自身"),

    /**
     * 一级
     */
    ONE(2, "一级"),

    /**
     * 二级
     */
    TWO(3, "二级"),

    /**
     * 三级
     */
    THREE(4, "三级");

    @EnumValue
    private final Integer code;
    private final String message;

    /**
     * 获取枚举类的键值对表达形式集合
     */
    public static List<KeyValuePair<String, String>> getKeyValuePairList() {
        return Arrays.stream(values()).map(it -> new KeyValuePair<>(it.name(), it.getMessage())).collect(Collectors.toList());
    }

    public static Optional<ProfitLevelEnum> getByNameOptional(String name) {
        return Arrays.stream(values()).filter(it -> it.name().toLowerCase().equals(name)).findAny();
    }

}
