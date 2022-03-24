package cn.fufeii.ds.common.enumerate;

/**
 * 结果枚举定义
 * 为项目中各种业务枚举或者通用枚举
 * 在返回结果时定义的统一标准
 *
 * @author FuFei
 * @date 2021/9/2
 */
public interface ResultDefinition {

    /**
     * 获取响应码
     *
     * @return -
     */
    int getCode();

    /**
     * 获取响应消息
     *
     * @return -
     */
    String getMsg();

    /**
     * 获取按照msg模板格式化后的响应消息
     *
     * @return -
     */
    default String getFormatMsg(String... params) {
        String msg = this.getMsg();
        if (params == null || params.length == 0) {
            return msg;
        }
        return String.format(msg, (Object[]) params);
    }

}
