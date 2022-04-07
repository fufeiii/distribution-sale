package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitLevelEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.ProfitEvent;
import cn.fufeii.ds.repository.entity.ProfitParam;
import cn.fufeii.ds.server.subscribe.event.InviteEvent;
import org.springframework.stereotype.Service;

/**
 * 邀请分润策略
 *
 * @author FuFei
 * @date 2022/3/22
 */
@Service
public class InviteProfitStrategy extends AbstractProfitStrategy {

    @Override
    public boolean match(ProfitTypeEnum profitType) {
        return ProfitTypeEnum.INVITE == profitType;
    }

    @Override
    public void profit(Object source) {
        InviteEvent.Source inviteEventInviteEvent = (InviteEvent.Source) source;

        // 记录分润事件
        ProfitEvent inviteEvent = new ProfitEvent();

        // 获取会员，遍历其从自身上级会员，并查找对应参数
        Member selfMember = crudMemberService.selectById(inviteEventInviteEvent.getMemberId());
        ProfitParam selfMoneyParam = super.getInviteProfitParam(AccountTypeEnum.MONEY, ProfitLevelEnum.SELF, selfMember.getIdentityType(), selfMember.getRankType());
        ProfitParam selfPointsParam = super.getInviteProfitParam(AccountTypeEnum.POINTS, ProfitLevelEnum.SELF, selfMember.getIdentityType(), selfMember.getRankType());

        super.doProfit(inviteEvent, selfMember, selfMoneyParam, selfPointsParam);

        // 查询一级邀请人

        // 查询二级邀请人

        // 查询三级邀请人


    }

}
