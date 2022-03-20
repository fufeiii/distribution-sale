package cn.fufeii.ds.portal.ps.event;

import org.springframework.context.ApplicationEvent;

/**
 * 邀请用户事件
 *
 * @author FuFei
 * @date 2022/3/20
 */
public class MoneyEvent extends ApplicationEvent {

    public MoneyEvent(Object source) {
        super(source);
    }

}
