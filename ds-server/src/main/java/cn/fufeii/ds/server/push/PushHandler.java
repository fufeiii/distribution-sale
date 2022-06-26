package cn.fufeii.ds.server.push;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;

/**
 * 推送处理器
 *
 * @author FuFei
 */
public interface PushHandler {

    /**
     * 推送消息
     *
     * @param notifyUrl   地址
     * @param sk          密钥
     * @param jsonMessage 消息(json格式)
     * @return *
     */
    NotifyStateEnum push(String notifyUrl, String sk, String jsonMessage);

}
