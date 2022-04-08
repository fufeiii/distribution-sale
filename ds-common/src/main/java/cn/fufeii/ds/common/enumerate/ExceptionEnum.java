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
    SERVER_API_AUTH_ERROR(5001, "", "鉴权失败：%s"),
    SERVER_SQL_UPDATE_FAIL(5101, "更新失败", ""),


    /* ========================= 管理台异常枚举 ========================= */
    ADMIN_COMMON_ERROR(6000, "管理台错误", ""),
    STATE_COMMON_ERROR(6001, "重复修改状态", ""),
    API_ADMIN_DENY_ERROR(6002, "超管无权限调用", ""),
    API_USER_DENY_ERROR(6003, "用户无权限调用", ""),
    API_FIELD_ERROR(6004, "", "参数错误：%s"),
    // ~ 6100 - 6110 端属于权限占位端
    LOGIN_ERROR(6101, "", "登录错误：%s"),
    JWT_ERROR(6102, "", "jwt错误：%s"),
    LOGOUT_ERROR(6103, "", "登出错误：%s"),
    // 业务创建类错误
    USER_CREATE_ERROR(6201, "", "创建用户错误：%s"),
    PLATFORM_CREATE_ERROR(6301, "", "创建平台错误：%s"),
    PROFIT_PARAM_CREATE_ERROR(6401, "", "创建分润参数错误：%s"),
    RANK_PARAM_CREATE_ERROR(6501, "", "创建段位数错误：%s"),

    /* ========================= 通用枚举 ========================= */
    ENTITY_NOT_EXIST(7001, "", "数据不存在：%s"),
    NO_DATA_PERMISSION(7011, "无数据权限", "无权限：%s"),


    /* ========================= 业务异常枚举 ========================= */
    NO_SUITABLE_PARAM(7102, "", "没有合适的[%s]参数"),
    UNKNOWN_STRATEGY(7101, "", "未知的[%s]的分销策略");


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
