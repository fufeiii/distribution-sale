package cn.fufeii.ds.server.push;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;
import cn.fufeii.ds.repository.crud.CrudAllotProfitEventService;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.server.model.api.request.AllotEventNotifyRequest;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.hutool.core.text.CharSequenceUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 推送服务
 *
 * @author FuFei
 * @date 2022/4/17
 */
@Slf4j
@Service
public class PushService {
    @Autowired
    private CrudAllotProfitEventService crudAllotProfitEventService;
    @Autowired
    private PushHandler pushHandler;


    /**
     * 推送分润事件到业务系统
     *
     * @param eventId 分润事件Id
     */
    @Async
    public void pushAllotProfitEvent(Long eventId) {
        AllotProfitEvent ape = crudAllotProfitEventService.selectById(eventId);
        Platform selfPlatform = CurrentPlatformHelper.self();
        String notifyUrl = selfPlatform.getNotifyUrl();
        if (CharSequenceUtil.isBlank(notifyUrl)) {
            log.warn("平台[{}]未设置分润通知地址", selfPlatform.getUsername());
            return;
        }

        // 组装参数
        AllotEventNotifyRequest allotEventNotifyRequest = new AllotEventNotifyRequest();
        allotEventNotifyRequest.setProfitType(ape.getProfitType());
        allotEventNotifyRequest.setTriggerMemberId(allotEventNotifyRequest.getTriggerMemberId());
        allotEventNotifyRequest.setEventNumber(allotEventNotifyRequest.getEventNumber());
        allotEventNotifyRequest.setEventAmount(ape.getEventAmount());
        allotEventNotifyRequest.setMemo(allotEventNotifyRequest.getMemo());

        // 发送数据
        NotifyStateEnum notifyStateEnum = NotifyStateEnum.FAIL;
        try {
            notifyStateEnum = pushHandler.push(notifyUrl, allotEventNotifyRequest);
        } catch (Exception e) {
            log.warn("分润事件推送失败", e);
        }

        // 更新通知状态
        ape.setNotifyState(notifyStateEnum);
        crudAllotProfitEventService.updateById(ape);
    }


}
