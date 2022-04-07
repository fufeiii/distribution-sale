package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.biz.*;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.crud.CrudProfitParamService;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.ProfitEvent;
import cn.fufeii.ds.repository.entity.ProfitParam;
import cn.fufeii.ds.server.strategy.ProfitStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * AbstractProfitStrategy
 *
 * @author FuFei
 * @date 2022/3/22
 */
public abstract class AbstractProfitStrategy implements ProfitStrategy {
    @Autowired
    protected CrudMemberService crudMemberService;
    @Autowired
    protected CrudProfitParamService crudProfitParamService;

    /**
     * 获取分润参数
     */
    private ProfitParam getProfitParam(AccountTypeEnum ate, ProfitTypeEnum pte, ProfitLevelEnum ple, MemberIdentityTypeEnum mite, MemberRankTypeEnum mre) {
        LambdaQueryWrapper<ProfitParam> lambdaQueryWrapper = Wrappers.<ProfitParam>lambdaQuery()
                .eq(ProfitParam::getAccountType, ate)
                .eq(ProfitParam::getProfitType, pte)
                .eq(ProfitParam::getProfitLevel, ple)
                .eq(ProfitParam::getMemberIdentityType, mite)
                .eq(ProfitParam::getMemberRankType, mre)
                .eq(ProfitParam::getState, StateEnum.ENABLE);
//        DataAuthorityHelper.setPlatform(lambdaQueryWrapper, platformUsername);
        List<ProfitParam> profitParamList = crudProfitParamService.selectList(lambdaQueryWrapper);
        if (profitParamList.size() > 1) {
            throw new BizException();
        }
        return profitParamList.isEmpty() ? null : profitParamList.get(0);
    }

    /**
     * 查询邀请分润参数
     */
    protected ProfitParam getInviteProfitParam(AccountTypeEnum ate, ProfitLevelEnum ple, MemberIdentityTypeEnum mite, MemberRankTypeEnum mre) {
        return this.getProfitParam(ate, ProfitTypeEnum.INVITE, ple, mite, mre);
    }


    /**
     * 执行分润逻辑
     * 计算佣金/积分数量，并入对应会员帐户
     */
    protected void doProfit(ProfitEvent event, Member member, ProfitParam moneyParam, ProfitParam pointsParam) {

        // 计算佣金分润参数的钱
        if (moneyParam != null) {
            // 保存佣金入账记录
            // 保存佣金分销记录
        }

        // 计算积分分润参数的积分
        if (pointsParam != null) {
            // 保存积分入账记录
            // 保存积分分销记录
        }


    }

}
