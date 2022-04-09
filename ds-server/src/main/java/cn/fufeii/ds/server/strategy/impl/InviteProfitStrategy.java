package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.repository.entity.ProfitEvent;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.InviteEvent;
import cn.hutool.core.text.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 邀请分润策略
 *
 * @author FuFei
 * @date 2022/3/22
 */
@Slf4j
@Service
public class InviteProfitStrategy extends AbstractProfitStrategy {

    @Override
    public boolean match(ProfitTypeEnum profitType) {
        return ProfitTypeEnum.INVITE == profitType;
    }

    @Override
    public void profit(Object source) {
        String platformUsername = CurrentPlatformHelper.username();
        log.info("【邀请分润】=====> 开始, 平台[{}]", platformUsername);
        InviteEvent.Source inviteEventInviteEvent = (InviteEvent.Source) source;

        // 查询出主要相关的会员
        Member inviteeMember = crudMemberService.selectById(inviteEventInviteEvent.getMemberId());
        Member firstInviterMember = crudMemberService.selectById(inviteeMember.getFirstInviterId());
        log.info("被邀请会员[{}], 邀请会员[{}]", inviteeMember.getUsername(), firstInviterMember.getUsername());

        // 记录分润事件
        ProfitEvent inviteEvent = this.saveProfitEvent(inviteeMember);

        // 获取当前会员
        super.executeProfit(inviteEvent, inviteeMember, ProfitTypeEnum.INVITE, ProfitLevelEnum.SELF);

        // 查询一级邀请人, 被邀请加入的肯定有一级邀请人
        super.executeProfit(inviteEvent, firstInviterMember, ProfitTypeEnum.INVITE, ProfitLevelEnum.ONE);

        // 查询二级邀请人
        Long secondInviterId = inviteeMember.getSecondInviterId();
        if (DsUtil.isValidMemberId(secondInviterId)) {
            Member member = crudMemberService.selectById(secondInviterId);
            log.info("存在二级邀请人{}", member.getUsername());
            super.executeProfit(inviteEvent, member, ProfitTypeEnum.INVITE, ProfitLevelEnum.TWO);
        }

        // 查询三级邀请人
        Long thirdInviterId = inviteeMember.getThirdInviterId();
        if (DsUtil.isValidMemberId(thirdInviterId)) {
            Member member = crudMemberService.selectById(thirdInviterId);
            log.info("存在三级邀请人{}", member.getUsername());
            super.executeProfit(inviteEvent, member, ProfitTypeEnum.INVITE, ProfitLevelEnum.THREE);
        }

        log.info("【邀请分润】<===== 结束, 平台[{}]", platformUsername);
    }


    /**
     * 保存分销事件
     */
    private ProfitEvent saveProfitEvent(Member inviteeMember) {
        ProfitEvent profitEvent = new ProfitEvent();
        Platform self = CurrentPlatformHelper.self();
        profitEvent.setPlatformUsername(self.getUsername());
        profitEvent.setPlatformNickname(self.getNickname());
        profitEvent.setProfitType(ProfitTypeEnum.INVITE);
        profitEvent.setTriggerMemberId(inviteeMember.getFirstInviterId());
        profitEvent.setEventNumber(inviteeMember.getFirstInviterId() + StrPool.DASHED + ProfitTypeEnum.INVITE.name());
        profitEvent.setEventAmount(DsServerConstant.DEFAULT_EVENT_AMOUNT);
        profitEvent.setMemo(String.format("用户[%s]被邀请加入", inviteeMember.getNickname()));
        return crudProfitEventService.insert(profitEvent);
    }

}
