package cn.fufeii.ds.common.enumerate.biz;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 分润类型枚举，作为分润参数存在。
 * 并且应该在业务上 应该存在对应的业务实现和相关接口
 *
 * @author Fu Fei
 * @date 2020/7/15
 */
@Getter
@AllArgsConstructor
public enum ProfitTypeEnum {

    /**
     * 交易
     */
    TRADE(1, "商品交易"),

    /**
     * 邀请
     */
    INVITE(2, "邀请好友"),

    /**
     * 升级
     */
    RANK(3, "段位升级");


    @EnumValue
    private final Integer code;
    private final String message;

}
