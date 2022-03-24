package cn.fufeii.ds.portal.ps.event;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * 基础的分销事件
 *
 * @author FuFei
 * @date 2022/3/24
 */
@Getter
public abstract class AbstractProfitEvent extends ApplicationEvent {

    protected final ProfitTypeEnum profitType;

    public AbstractProfitEvent(ProfitTypeEnum profitType, Object source) {
        super(source);
        this.profitType = profitType;
    }

}
