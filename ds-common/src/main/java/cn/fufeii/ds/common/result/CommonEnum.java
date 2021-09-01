package cn.fufeii.ds.common.result;

/**
 * 公共枚举
 *
 * @author FuFei
 * @date 2021/8/22
 */
public enum CommonEnum implements ResultDefinition {

    /**
     * 成功
     */
    SUCCESS(0, "成功");


    private final int code;
    private final String msg;

    CommonEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static boolean isSuccess(int code) {
        return SUCCESS.code == code;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

}
