package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.repository.entity.ProfitEvent;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.UpgradeEvent;
import cn.hutool.core.text.StrPool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 段位升级分润策略
 *
 * @author FuFei
 * @date 2022/4/9
 */
@Slf4j
@Service
public class UpgradeProfitStrategy extends AbstractProfitStrategy {


    @Override
    public boolean match(ProfitTypeEnum profitType) {
        return ProfitTypeEnum.UPGRADE == profitType;
    }

    @Override
    public void profit(Object source) {
        String platformUsername = CurrentPlatformHelper.username();
        log.info("【段位分润】=====> 开始, 平台[{}]", platformUsername);
        UpgradeEvent.Source upgradeEventEventInviteEvent = (UpgradeEvent.Source) source;

        // 查询出主要相关的会员
        Member upgradeMember = crudMemberService.selectById(upgradeEventEventInviteEvent.getMemberId());

        // 记录分润事件
        ProfitEvent upgradeEvent = this.saveProfitEvent(upgradeMember);

        // 当前会员进行分润
        super.executeProfit(upgradeEvent, upgradeMember, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.SELF);

        // 查询一级邀请人
        Long firstInviterId = upgradeMember.getFirstInviterId();
        if (DsUtil.isValidMemberId(firstInviterId)) {
            Member member = crudMemberService.selectById(firstInviterId);
            log.info("存在一级邀请人{}", member.getUsername());
            super.executeProfit(upgradeEvent, member, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.ONE);
        }

        // 查询二级邀请人
        Long secondInviterId = upgradeMember.getSecondInviterId();
        if (DsUtil.isValidMemberId(secondInviterId)) {
            Member member = crudMemberService.selectById(secondInviterId);
            log.info("存在二级邀请人{}", member.getUsername());
            super.executeProfit(upgradeEvent, member, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.TWO);
        }

        // 查询三级邀请人
        Long thirdInviterId = upgradeMember.getThirdInviterId();
        if (DsUtil.isValidMemberId(thirdInviterId)) {
            Member member = crudMemberService.selectById(thirdInviterId);
            log.info("存在三级邀请人{}", member.getUsername());
            super.executeProfit(upgradeEvent, member, ProfitTypeEnum.UPGRADE, ProfitLevelEnum.THREE);
        }

        log.info("【段位分润】<===== 结束, 平台[{}]", platformUsername);
    }


    /**
     * 保存分销事件
     */
    private ProfitEvent saveProfitEvent(Member upgradeMember) {
        ProfitEvent profitEvent = new ProfitEvent();
        Platform self = CurrentPlatformHelper.self();
        profitEvent.setPlatformUsername(self.getUsername());
        profitEvent.setPlatformNickname(self.getNickname());
        profitEvent.setProfitType(ProfitTypeEnum.UPGRADE);
        profitEvent.setTriggerMemberId(upgradeMember.getFirstInviterId());
        profitEvent.setEventNumber(upgradeMember.getFirstInviterId() + StrPool.DASHED + ProfitTypeEnum.INVITE.name());
        profitEvent.setEventAmount(DsServerConstant.DEFAULT_EVENT_AMOUNT);
        profitEvent.setMemo(String.format("用户[%s]段位升级", upgradeMember.getNickname()));
        return crudProfitEventService.insert(profitEvent);
    }

}
