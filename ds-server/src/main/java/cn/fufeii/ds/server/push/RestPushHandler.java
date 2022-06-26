package cn.fufeii.ds.server.push;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.config.property.DsServerProperties;
import cn.fufeii.ds.server.security.ApiAuthenticationFilter;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.text.StrPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * @author FuFei
 */
@Component
public class RestPushHandler implements PushHandler {
    @Autowired
    private DsServerProperties dsServerProperties;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public NotifyStateEnum push(String notifyUrl, String sk, String jsonMessage) {

        // 组装请求头
        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_JSON);

        // 生成签名
//        if (dsServerProperties.getEnableApiSignature()) {
        if (true) {
            String notifyUrlTemp = notifyUrl.replaceFirst(StrPool.SLASH + StrPool.SLASH, CharSequenceUtil.EMPTY);
            int indexOf = notifyUrlTemp.indexOf(StrPool.SLASH);
            String path = CharSequenceUtil.EMPTY;
            if (indexOf > 0) {
                path = notifyUrlTemp.substring(indexOf);
            }
            String waitSignatureStr = ApiAuthenticationFilter.generateWaitSignatureStr(HttpMethod.POST.name(), path, jsonMessage);
            header.set(HttpHeaders.AUTHORIZATION, ApiAuthenticationFilter.generateSignature(sk, waitSignatureStr));
        }

        // 通知上游系统
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonMessage, header);
        String response = restTemplate.postForObject(notifyUrl, httpEntity, String.class);
        return DsServerConstant.NOTIFY_SUCCESS_FLAG.equals(response) ? NotifyStateEnum.SUCCESS : NotifyStateEnum.FAIL;
    }

}
