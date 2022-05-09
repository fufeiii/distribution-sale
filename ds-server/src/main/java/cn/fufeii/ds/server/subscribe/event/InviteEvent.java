package cn.fufeii.ds.server.subscribe.event;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import lombok.Data;

/**
 * 邀请好友事件
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

        /**
         * 被邀请人
         */
        private Long inviteeMemberId;

        /**
         * 邀请人
         */
        private Long inviteMemberId;

    }

}
