package cn.fufeii.ds.server.subscribe.event;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;

/**
 * 邀请用户事件
 *
 * @author FuFei
 * @date 2022/3/20
 */
public class InviteEvent extends AbstractProfitEvent {

    public InviteEvent(ProfitTypeEnum profitType, Object source) {
        super(profitType, source);
    }

}
