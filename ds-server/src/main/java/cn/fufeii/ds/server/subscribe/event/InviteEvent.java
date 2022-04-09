package cn.fufeii.ds.server.subscribe.event;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import lombok.Data;

/**
 * 邀请用户事件
 *
 * @author FuFei
 * @date 2022/3/20
 */
public class InviteEvent extends AbstractProfitEvent {

    public InviteEvent(ProfitTypeEnum profitType, Source source) {
        super(profitType, source);
    }

    @Data
    public static class Source {

        private Long memberId;

    }

}
