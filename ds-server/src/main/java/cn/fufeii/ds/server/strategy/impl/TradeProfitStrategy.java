package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.TradeEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 金钱交易分润策略
 *
 * @author FuFei
 * @date 2022/4/9
 */
@Slf4j
@Service
public class TradeProfitStrategy extends AbstractProfitStrategy {

    @Override
    public boolean match(ProfitTypeEnum profitType) {
        return ProfitTypeEnum.TRADE == profitType;
    }

    @Override
    protected AllotProfitEvent saveEvent(Object source) {
        TradeEvent.Source tradeEventSource = (TradeEvent.Source) source;
        // 查询出主要相关的会员
        Member tradeMember = crudMemberService.selectById(tradeEventSource.getMemberId());
        AllotProfitEvent profitEvent = new AllotProfitEvent();
        Platform self = CurrentPlatformHelper.self();
        profitEvent.setPlatformUsername(self.getUsername());
        profitEvent.setPlatformNickname(self.getNickname());
        profitEvent.setProfitType(ProfitTypeEnum.TRADE);
        profitEvent.setTriggerMemberId(tradeMember.getId());
        profitEvent.setEventNumber(tradeEventSource.getTradeNumber());
        profitEvent.setEventAmount(tradeEventSource.getTradeAmount());
        profitEvent.setMemo(String.format("会员[%s]发生了金钱交易", tradeMember.getNickname()));
        return crudAllotProfitEventService.insert(profitEvent);
    }

    @Override
    protected void allotProfit(Object source, AllotProfitEvent ape) {
        String platformUsername = CurrentPlatformHelper.username();
        log.info("【交易分润】=====> 开始, 平台[{}]", platformUsername);
        TradeEvent.Source tradeEventSource = (TradeEvent.Source) source;

        // 查询出主要相关的会员
        Member tradeMember = crudMemberService.selectById(tradeEventSource.getMemberId());

        // 当前会员进行分润
        super.tryDoAllotProfit(ape, tradeMember, ProfitTypeEnum.TRADE, ProfitLevelEnum.SELF);

        // 查询一级邀请人
        Long firstInviterId = tradeMember.getFirstInviterId();
        if (DsUtil.isValidMemberId(firstInviterId)) {
            Member member = crudMemberService.selectById(firstInviterId);
            log.info("【交易分润】存在一级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.TRADE, ProfitLevelEnum.ONE);
        }

        // 查询二级邀请人
        Long secondInviterId = tradeMember.getSecondInviterId();
        if (DsUtil.isValidMemberId(secondInviterId)) {
            Member member = crudMemberService.selectById(secondInviterId);
            log.info("【交易分润】存在二级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.TRADE, ProfitLevelEnum.TWO);
        }

        // 查询三级邀请人
        Long thirdInviterId = tradeMember.getThirdInviterId();
        if (DsUtil.isValidMemberId(thirdInviterId)) {
            Member member = crudMemberService.selectById(thirdInviterId);
            log.info("【交易分润】存在三级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.TRADE, ProfitLevelEnum.THREE);
        }

        log.info("【交易分润】<===== 结束, 平台[{}]", platformUsername);
    }

}
