package cn.fufeii.ds.portal.ps.event;

import org.springframework.context.ApplicationEvent;

/**
 * 邀请用户事件
 *
 * @author FuFei
 * @date 2022/3/20
 */
public class InviteEvent extends ApplicationEvent {

    public InviteEvent(Object source) {
        super(source);
    }

}
