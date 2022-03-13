package cn.fufeii.ds.common.enumerate.biz;

import cn.fufeii.ds.common.util.KeyValuePair;
import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户身份标识的枚举
 *
 * @author Fu Fei
 * @date 2020/7/11
 */
@Getter
@AllArgsConstructor
public enum MemberIdentityTypeEnum {

    /**
     * 普通用户
     */
    GENERAL(1, "普通用户"),
    /**
     * 高级用户
     */
    ADVANCE(2, "高级用户"),
    /**
     * 专业用户
     */
    PROFESSION(3, "专业用户");

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
