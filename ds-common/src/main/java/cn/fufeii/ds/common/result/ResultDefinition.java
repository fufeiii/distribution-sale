package cn.fufeii.ds.common.result;

/**
 * 结果定义
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

}
