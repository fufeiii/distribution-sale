package cn.fufeii.ds.server.strategy;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.server.push.PushService;
import cn.fufeii.ds.server.subscribe.event.AbstractProfitEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author FuFei
 */
@Slf4j
@Service
public class AllotProfitStrategyServiceImpl implements AllotProfitStrategyService {
    @Autowired
    private List<AllotProfitStrategy> allotProfitStrategyList;
    @Autowired
    private PushService pushService;

    @Override
    public void startAllotProfit(AbstractProfitEvent profitEvent) {
        // 1. 寻找合适的分润策略
        log.info("寻找分润策略");
        ProfitTypeEnum profitType = profitEvent.getProfitType();
        AllotProfitStrategy allotProfitStrategy = allotProfitStrategyList.stream().filter(it -> it.support(profitType))
                .findFirst()
                .orElseThrow(() -> BizException.server(String.format("未知的[%s]的分润策略", profitType.name())));

        // 2. 保存分润事件
        log.info("保存分润事件");
        Object eventSource = profitEvent.getSource();
        AllotProfitEvent allotProfitEvent = allotProfitStrategy.saveEvent(eventSource);
        allotProfitStrategy.allotProfit(eventSource, allotProfitEvent);

        // 3. 推送分润事件
        log.info("推送分润事件");
        pushService.pushAllotProfitEvent(allotProfitEvent.getId());
    }

}
