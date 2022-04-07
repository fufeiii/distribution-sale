package cn.fufeii.ds.server.strategy;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.server.subscribe.event.AbstractProfitEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author FuFei
 * @date 2022/3/22
 */
@Component
public class ProfitStrategyHolder {
    @Autowired
    private List<ProfitStrategy> profitStrategyList;

    /**
     * 根据对应的事件执行与之匹配的分润策略
     */
    public void profit(AbstractProfitEvent profitEvent) {
        ProfitTypeEnum profitType = profitEvent.getProfitType();
        profitStrategyList.stream().filter(it -> it.match(profitType))
                .findFirst()
                .orElseThrow(() -> BizException.serverError(ExceptionEnum.UNKNOWN_STRATEGY, profitType.name()))
                .profit(profitEvent.getSource());
    }

}
