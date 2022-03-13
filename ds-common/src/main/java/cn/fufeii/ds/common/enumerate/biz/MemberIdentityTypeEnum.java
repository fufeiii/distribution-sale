package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

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

}
