package cn.fufeii.ds.server.subscribe.listener;

import cn.fufeii.ds.server.strategy.AllotProfitStrategyService;
import cn.fufeii.ds.server.subscribe.event.InviteEvent;
import cn.fufeii.ds.server.subscribe.event.TradeEvent;
import cn.fufeii.ds.server.subscribe.event.UpgradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 分润事件监听器
 *
 * @author FuFei
 * @date 2022/3/20
 */
@Slf4j
@Component
public class ProfitEventListener {

    @Autowired
    private AllotProfitStrategyService allotProfitStrategyService;

    /**
     * 监听会员邀请事件
     */
    @Async
    @TransactionalEventListener
    public void handle(InviteEvent inviteEvent) {
        log.info("执行邀请分润机制");
        allotProfitStrategyService.startAllotProfit(inviteEvent);
    }

    /**
     * 监听段位升级事件
     */
    @Async
    @TransactionalEventListener
    public void handle(UpgradeEvent upgradeEvent) {
        log.info("执行邀请分润机制");
        allotProfitStrategyService.startAllotProfit(upgradeEvent);
    }


    /**
     * 监听金钱交易事件
     * 通过接口调用来的，这里是同步
     */
    @EventListener
    public void handle(TradeEvent tradeEvent) {
        log.info("执行交易分润机制");
        allotProfitStrategyService.startAllotProfit(tradeEvent);
    }


}