package cn.fufeii.ds.portal.stgy;

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
     */
    void profit();

}
