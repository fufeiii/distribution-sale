package cn.fufeii.ds.common.enumerate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 异常公共枚举
 *
 * @author FuFei
 * @date 2021/8/22
 */
@Getter
@AllArgsConstructor
public enum ExceptionEnum implements ResultDefinition {

    SUCCESS(0, "成功"),

    /* ========================= 客户端异常枚举 ========================= */
    CLIENT_ERROR(4000, "客户端错误"),

    /* ========================= 服务端异常枚举 ========================= */
    SERVER_ERROR(5000, "服务端错误"),


    /* ========================= 管理台异常枚举 ========================= */
    ADMIN_ERROR(6000, "管理台错误"),
    LOGIN_ERROR(6001, "登录错误：%s"),
    JWT_ERROR(6002, "jwt错误"),
    LOGOUT_ERROR(6003, "登出错误：%s"),

    /* ========================= 业务异常枚举 ========================= */
    UNKNOWN_STRATEGY(7001, "未知的[%s]的分销策略");


    private final int code;
    private final String msg;

}
