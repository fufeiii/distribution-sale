package cn.fufeii.ds.server.strategy;

import cn.fufeii.ds.server.subscribe.event.AbstractProfitEvent;

/**
 * AllotProfitService
 *
 * @author FuFei
 * @date 2022/4/27
 */
public interface AllotProfitStrategyService {


    /**
     * 开始分润
     *
     * @param profitEvent 引发分润的来源
     */
    void startAllotProfit(AbstractProfitEvent profitEvent);


}
