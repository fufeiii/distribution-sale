package cn.fufeii.ds.server.push;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;
import cn.fufeii.ds.common.util.ObjectMapperUtil;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.model.api.request.AllotEventNotifyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author FuFei
 */
@Component
public class RestPushHandler implements PushHandler {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public NotifyStateEnum push(String notifyUrl, AllotEventNotifyRequest request) {
        // 通知上游系统
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(ObjectMapperUtil.toJsonString(request), header);
        String response = restTemplate.postForObject(notifyUrl, httpEntity, String.class);
        return DsServerConstant.NOTIFY_SUCCESS_FLAG.equals(response) ? NotifyStateEnum.SUCCESS : NotifyStateEnum.FAIL;
    }

}
