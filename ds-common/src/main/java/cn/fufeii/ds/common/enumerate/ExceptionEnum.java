package cn.fufeii.ds.common.enumerate;

import lombok.AllArgsConstructor;

/**
 * 异常公共枚举
 *
 * @author FuFei
 * @date 2021/8/22
 */
@AllArgsConstructor
public enum ExceptionEnum implements ResultDefinition {

    SUCCESS(0, "成功", ""),

    /* ========================= 客户端异常枚举 ========================= */
    CLIENT_ERROR(4000, "客户端错误", ""),

    /* ========================= 服务端异常枚举 ========================= */
    SERVER_ERROR(5000, "服务端错误", ""),


    /* ========================= 管理台异常枚举 ========================= */
    ADMIN_ERROR(6000, "管理台错误", ""),
    // ~ 6100 - 6110 端属于权限占位端
    LOGIN_ERROR(6101, "", "登录错误：%s"),
    JWT_ERROR(6102, "", "jwt错误：%s"),
    LOGOUT_ERROR(6103, "", "登出错误：%s"),

    /* ========================= 业务异常枚举 ========================= */
    UNKNOWN_STRATEGY(7001, "", "未知的[%s]的分销策略");


    private final int code;
    private final String dftMsg;
    private final String fmtMsg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg(String... params) {
        return params.length == 0 ? dftMsg : String.format(fmtMsg, (Object[]) params);
    }

}
