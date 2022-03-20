package cn.fufeii.ds.portal.ps.event;

import org.springframework.context.ApplicationEvent;

/**
 * 邀请用户事件
 *
 * @author FuFei
 * @date 2022/3/20
 */
public class UpgradeEvent extends ApplicationEvent {

    public UpgradeEvent(Object source) {
        super(source);
    }

}
