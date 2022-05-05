package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.strategy.AllotProfitStrategy;
import cn.fufeii.ds.server.subscribe.event.UpgradeEvent;
import cn.hutool.core.date.SystemClock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 段位升级分润策略
 *
 * @author FuFei
 */
@Slf4j
@Service
public class UpgradeProfitStrategy extends BaseAllotProfit implements AllotProfitStrategy {

    @Override
    public boolean support(ProfitTypeEnum profitType) {
        return ProfitTypeEnum.UPGRADE == profitType;
    }

    @Override
    public AllotProfitEvent saveEvent(Object source) {
        UpgradeEvent.Source upgradeEventSource = (UpgradeEvent.Source) source;
        // 查询出主要相关的会员
        Member upgradeMember = crudMemberService.selectById(upgradeEventSource.getMemberId());
        AllotProfitEvent profitEvent = new AllotProfitEvent();
        Platform self = CurrentPlatformHelper.self();
        profitEvent.setPlatformUsername(self.getUsername());
        profitEvent.setPlatformNickname(self.getNickname());
        profitEvent.setProfitType(ProfitTypeEnum.UPGRADE);
        profitEvent.setTriggerMemberId(upgradeMember.getId());
        profitEvent.setEventNumber(upgradeMember.getId() + "U" + (SystemClock.now() / 1000));
        profitEvent.setEventAmount(DsServerConstant.DEFAULT_EVENT_AMOUNT);
        profitEvent.setMemo(String.format("会员[%s]发生了段位升级", upgradeMember.getNickname()));
        return crudAllotProfitEventService.insert(profitEvent);
    }

    @Override
    public void allotProfit(Object source, AllotProfitEvent ape) {
        String platformUsername = CurrentPlatformHelper.username();
        log.info("【升级分润】=====> 开始, 平台[{}]", platformUsername);
        UpgradeEvent.Source upgradeEventSource = (UpgradeEvent.Source) source;

        // 查询出主要相关的会员
        Member upgradeMember = crudMemberService.selectById(upgradeEventSource.getMemberId());

        // 当前会员进行分润
        super.tryDoAllotProfit(ape, upgradeMember, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.SELF);

        // 查询一级邀请人
        Long firstInviterId = upgradeMember.getFirstInviterId();
        if (DsUtil.isValidMemberId(firstInviterId)) {
            Member member = crudMemberService.selectById(firstInviterId);
            log.info("【升级分润】存在一级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.ONE);
        }

        // 查询二级邀请人
        Long secondInviterId = upgradeMember.getSecondInviterId();
        if (DsUtil.isValidMemberId(secondInviterId)) {
            Member member = crudMemberService.selectById(secondInviterId);
            log.info("【升级分润】存在二级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.TWO);
        }

        // 查询三级邀请人
        Long thirdInviterId = upgradeMember.getThirdInviterId();
        if (DsUtil.isValidMemberId(thirdInviterId)) {
            Member member = crudMemberService.selectById(thirdInviterId);
            log.info("【升级分润】存在三级邀请人{}", member.getUsername());
            super.tryDoAllotProfit(ape, member, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.THREE);
        }

        log.info("【升级分润】<===== 结束, 平台[{}]", platformUsername);
    }

}
