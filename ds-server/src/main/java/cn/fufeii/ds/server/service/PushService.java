package cn.fufeii.ds.server.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 推送服务
 *
 * @author FuFei
 * @date 2022/4/17
 */
@Service
public class PushService {


    /**
     * 推送分销事件到业务系统
     */
    @Async
    public void pushAllotProfitEvent() {

    }


}
