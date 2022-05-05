package cn.fufeii.ds.server.strategy;

import cn.fufeii.ds.server.subscribe.event.AbstractProfitEvent;

/**
 * 分润策略服务
 *
 * @author FuFei
 */
public interface AllotProfitStrategyService {

    /**
     * 开始分润
     *
     * @param profitEvent 引发分润的来源
     */
    void startAllotProfit(AbstractProfitEvent profitEvent);

}
