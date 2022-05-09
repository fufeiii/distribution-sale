package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.biz.NotifyStateEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.strategy.AllotProfitStrategy;
import cn.fufeii.ds.server.subscribe.event.InviteEvent;
import cn.hutool.core.date.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 邀请分润策略
 *
 * @author FuFei
 */
@Slf4j
@Service
public class InviteProfitStrategy extends BaseAllotProfit implements AllotProfitStrategy {

    @Override
    public boolean support(ProfitTypeEnum profitType) {
        return ProfitTypeEnum.INVITE == profitType;
    }

    @Override
    public AllotProfitEvent saveEvent(Object source) {
        InviteEvent.Source inviteEventSource = (InviteEvent.Source) source;
        Member inviteeMember = crudMemberService.selectById(inviteEventSource.getInviteeMemberId());
        Member inviteMember = crudMemberService.selectById(inviteEventSource.getInviteMemberId());
        AllotProfitEvent profitEvent = new AllotProfitEvent();
        Platform self = CurrentPlatformHelper.self();
        profitEvent.setPlatformUsername(self.getUsername());
        profitEvent.setPlatformNickname(self.getNickname());
        profitEvent.setProfitType(ProfitTypeEnum.INVITE);
        profitEvent.setTriggerMemberId(inviteeMember.getId());
        profitEvent.setNotifyState(NotifyStateEnum.INIT);
        profitEvent.setEventNumber(inviteeMember.getId() + "IVT" + (SystemClock.now() / 1000));
        profitEvent.setEventAmount(DsServerConstant.DEFAULT_EVENT_AMOUNT);
        profitEvent.setMemo(String.format("会员[%s]邀请了[%s]加入", inviteeMember.getNickname(), inviteMember.getNickname()));
        return crudAllotProfitEventService.insert(profitEvent);
    }


    @Override
    public void allotProfit(Object source, AllotProfitEvent ape) {
        String platformUsername = CurrentPlatformHelper.username();
        log.info("【邀请分润】=====> 开始, 平台[{}]", platformUsername);
        InviteEvent.Source inviteEventSource = (InviteEvent.Source) source;

        // 查询出主要相关的会员
        Member inviteeMember = crudMemberService.selectById(inviteEventSource.getInviteeMemberId());
        Member firstInviterMember = crudMemberService.selectById(inviteeMember.getFirstInviterId());
        log.info("【邀请分润】被邀请会员[{}], 邀请会员[{}]", inviteeMember.getUsername(), firstInviterMember.getUsername());

        // 获取当前会员
        super.tryDoAllotProfit(ape, inviteeMember, ProfitTypeEnum.INVITE, ProfitLevelEnum.SELF);

        // 查询一级邀请人, 被邀请加入的肯定有一级邀请人
        super.tryDoAllotProfit(ape, firstInviterMember, ProfitTypeEnum.INVITE, ProfitLevelEnum.ONE);

        // 查询二级邀请人
        Long secondInviterId = inviteeMember.getSecondInviterId();
        if (DsUtil.isValidMemberId(secondInviterId)) {
            Member member = crudMemberService.selectById(secondInviterId);
            log.info("【邀请分润】存在二级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.INVITE, ProfitLevelEnum.TWO);
        }

        // 查询三级邀请人
        Long thirdInviterId = inviteeMember.getThirdInviterId();
        if (DsUtil.isValidMemberId(thirdInviterId)) {
            Member member = crudMemberService.selectById(thirdInviterId);
            log.info("【邀请分润】存在三级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.INVITE, ProfitLevelEnum.THREE);
        }

        log.info("【邀请分润】<===== 结束, 平台[{}]", platformUsername);
    }


}
