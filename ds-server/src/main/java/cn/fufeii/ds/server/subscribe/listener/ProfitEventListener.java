package cn.fufeii.ds.server.subscribe.listener;

import cn.fufeii.ds.server.strategy.ProfitStrategyHolder;
import cn.fufeii.ds.server.subscribe.event.InviteEvent;
import cn.fufeii.ds.server.subscribe.event.MoneyEvent;
import cn.fufeii.ds.server.subscribe.event.UpgradeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 分销事件监听器
 *
 * @author FuFei
 * @date 2022/3/20
 */
@Component
public class ProfitEventListener {

    @Autowired
    private ProfitStrategyHolder profitStrategyHolder;

    /**
     * 监听会员邀请事件
     */
    @Async
    @TransactionalEventListener
    public void handle(InviteEvent inviteEvent) {
        // 执行邀请分润机制
        profitStrategyHolder.profit(inviteEvent);
    }

    /**
     * 监听段位升级事件
     */
    @Async
    @TransactionalEventListener
    public void handle(UpgradeEvent upgradeEvent) {
        // 执行邀请分润机制
        profitStrategyHolder.profit(upgradeEvent);
    }


    /**
     * 监听货币交易事件
     */
    @EventListener
    public void handle(MoneyEvent moneyEvent) {
        // 执行交易分润机制
        profitStrategyHolder.profit(moneyEvent);
    }


}