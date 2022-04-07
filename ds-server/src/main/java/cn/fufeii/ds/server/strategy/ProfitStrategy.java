package cn.fufeii.ds.server.strategy;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;

/**
 * @author FuFei
 * @date 2022/3/22
 */
public interface ProfitStrategy {


    /**
     * 根据自身的分润策略匹配对应的分润事件
     */
    boolean match(ProfitTypeEnum profitType);

    /**
     * 分润
     *
     * @param source 引发分润的来源
     */
    void profit(Object source);

}
