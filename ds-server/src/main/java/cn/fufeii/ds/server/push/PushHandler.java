package cn.fufeii.ds.server.push;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;
import cn.fufeii.ds.server.model.api.request.AllotEventNotifyRequest;

/**
 * 推送处理器
 *
 * @author FuFei
 */
public interface PushHandler {

    /**
     * 执行推送动作
     *
     * @param notifyUrl -
     * @param request   -
     * @return
     */
    NotifyStateEnum push(String notifyUrl, AllotEventNotifyRequest request);

}
