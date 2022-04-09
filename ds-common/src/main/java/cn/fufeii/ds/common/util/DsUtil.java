package cn.fufeii.ds.common.util;

import cn.fufeii.ds.common.constant.DsConstant;
import cn.hutool.core.text.StrPool;

/**
 * 项目工具类
 *
 * @author FuFei
 * @date 2022/4/3
 */
public final class DsUtil {

    /**
     * 分转元, 实际就是移动小数点
     */
    public static String fenToYuan(Integer amount) {
        String amountStr = amount.toString();
        int length = amountStr.length();
        if (length == 1) {
            return "0.0" + amountStr;
        }
        if (length == 2) {
            return "0." + amountStr;
        }
        // 其余的都是大于1元的, 直接移动小数点

        return amountStr.substring(0, length - 2) + StrPool.DOT + amountStr.substring(length - 2);
    }

    /**
     * 判断当前会员是否为默认值
     */
    public static boolean isValidMemberId(Long memberId) {
        return !DsConstant.NULL_MEMBER_INVITER_ID.equals(memberId);
    }

}
