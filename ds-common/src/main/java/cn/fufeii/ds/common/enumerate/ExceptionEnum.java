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

    SUCCESS(0, "成功"),

    /* ========================= 客户端异常枚举 ========================= */
    CLIENT_ERROR(4000, "客户端错误"),

    /* ========================= 服务端异常枚举 ========================= */
    SERVER_ERROR(5000, "服务端错误"),

    LOGIN_IN_ERROR(5101, "登录错误"),

    ENTITY_NOT_EXIST(5201, "数据不存在"),
    ENTITY_UPDATE_FAIL(5202, "数据更新失败"),

    UPDATE_STATE_REPEATEDLY(5303, "重复更新状态"),

    /* ========================= 管理台异常枚举 ========================= */
    ADMIN_ERROR(6000, "管理台错误"),

    /* ========================= 通用枚举 ========================= */
    NO_DATA_PERMISSION(7011, "无数据权限");

    private final int code;
    private final String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
