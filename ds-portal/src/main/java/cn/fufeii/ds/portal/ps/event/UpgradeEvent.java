package cn.fufeii.ds.portal.ps.event;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;

/**
 * 邀请用户事件
 *
 * @author FuFei
 * @date 2022/3/20
 */
public class UpgradeEvent extends AbstractProfitEvent {

    public UpgradeEvent(ProfitTypeEnum profitType, Object source) {
        super(profitType, source);
    }

}
