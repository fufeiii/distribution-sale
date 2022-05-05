package cn.fufeii.ds.server.strategy;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;

/**
 * 分润策略抽象
 *
 * @author FuFei
 */
public interface AllotProfitStrategy {

    /**
     * 是否支持给定的分润类型
     *
     * @param profitType 分润类型
     */
    boolean support(ProfitTypeEnum profitType);


    /**
     * 保存分润事件
     *
     * @param eventSource 事件来源
     * @return 对应的分润事件
     */
    AllotProfitEvent saveEvent(Object eventSource);


    /**
     * 分润
     *
     * @param eventSource      事件来源
     * @param allotProfitEvent 分润事件
     */
    void allotProfit(Object eventSource, AllotProfitEvent allotProfitEvent);

}
