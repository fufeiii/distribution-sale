package cn.fufeii.ds.server.service;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;
import cn.fufeii.ds.common.util.ObjectMapperUtil;
import cn.fufeii.ds.repository.crud.CrudAllotProfitEventService;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.model.api.request.AllotEventNotifyRequest;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 推送服务
 *
 * @author FuFei
 * @date 2022/4/17
 */
@Service
public class PushService {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private CrudAllotProfitEventService crudAllotProfitEventService;


    /**
     * 推送分润事件到业务系统
     *
     * @param ape 分润事件
     */
    @Async
    public void pushAllotProfitEvent(AllotProfitEvent ape) {
        Platform selfPlatform = CurrentPlatformHelper.self();
        String notifyUrl = selfPlatform.getNotifyUrl();

        // 组装参数
        AllotEventNotifyRequest allotEventNotifyRequest = new AllotEventNotifyRequest();
        allotEventNotifyRequest.setProfitType(ape.getProfitType());
        allotEventNotifyRequest.setTriggerMemberId(allotEventNotifyRequest.getTriggerMemberId());
        allotEventNotifyRequest.setEventNumber(allotEventNotifyRequest.getEventNumber());
        allotEventNotifyRequest.setEventAmount(ape.getEventAmount());
        allotEventNotifyRequest.setMemo(allotEventNotifyRequest.getMemo());

        // 通知上游系统
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(ObjectMapperUtil.toJsonString(allotEventNotifyRequest), header);
        String response = restTemplate.postForObject(notifyUrl, httpEntity, String.class);

        // 更新通知状态
        NotifyStateEnum notifyStateEnum = NotifyStateEnum.FAIL;
        if (DsServerConstant.NOTIFY_SUCCESS_FLAG.equals(response)) {
            notifyStateEnum = NotifyStateEnum.SUCCESS;
        }
        ape.setNotifyState(notifyStateEnum);
        crudAllotProfitEventService.updateById(ape);

    }


}
