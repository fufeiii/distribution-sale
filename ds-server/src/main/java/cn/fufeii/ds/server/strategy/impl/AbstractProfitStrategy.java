package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.*;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.config.DataAuthorityHelper;
import cn.fufeii.ds.repository.crud.*;
import cn.fufeii.ds.repository.entity.*;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.strategy.ProfitStrategy;
import cn.fufeii.ds.server.subscribe.event.UpgradeEvent;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * AbstractProfitStrategy
 *
 * @author FuFei
 * @date 2022/3/22
 */
@Slf4j
public abstract class AbstractProfitStrategy implements ProfitStrategy {
    @Autowired
    protected CrudRankParamService crudRankParamService;
    @Autowired
    protected CrudMemberService crudMemberService;
    @Autowired
    protected CrudAccountService crudAccountService;
    @Autowired
    protected CrudProfitParamService crudProfitParamService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    protected CrudProfitEventService crudProfitEventService;
    @Autowired
    protected CrudProfitRecordService crudProfitRecordService;
    @Autowired
    protected CrudAccountRecordService crudAccountRecordService;

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
        DataAuthorityHelper.setPlatform(lambdaQueryWrapper, CurrentPlatformHelper.username());
        List<ProfitParam> profitParamList = crudProfitParamService.selectList(lambdaQueryWrapper);
        if (profitParamList.size() > 1) {
            throw new BizException(ExceptionEnum.NO_SUITABLE_PARAM, ate.getMessage() + "分润");
        }
        return profitParamList.isEmpty() ? null : profitParamList.get(0);
    }

    /**
     * 获取段位参数
     */
    private RankParam getRankParam(MemberRankTypeEnum mre) {
        LambdaQueryWrapper<RankParam> lambdaQueryWrapper = Wrappers.<RankParam>lambdaQuery()
                .eq(RankParam::getMemberRankType, mre)
                .eq(RankParam::getState, StateEnum.ENABLE);
        DataAuthorityHelper.setPlatform(lambdaQueryWrapper, CurrentPlatformHelper.username());
        List<RankParam> rankParamList = crudRankParamService.selectList(lambdaQueryWrapper);
        if (rankParamList.size() > 1) {
            throw new BizException(ExceptionEnum.NO_SUITABLE_PARAM, "段位");
        }
        return rankParamList.isEmpty() ? null : rankParamList.get(0);
    }

    /**
     * 计算分润数量（四舍五入）
     * 如果是佣金，计算单位为分
     *
     * @param profitParam *
     * @param baseAmount  基础金额
     */
    private Integer calculateProfitAmount(ProfitParam profitParam, Integer baseAmount) {
        // 只有是 百分比计算才需要进一步计算，因为其他情况都时固定分润的范畴，直接取分润比列就好了
        if (CalculateModeEnum.PERCENTAGE.equals(profitParam.getCalculateMode())) {
            BigDecimal ratioPercentage = new BigDecimal(profitParam.getProfitRatio().toString()).divide(new BigDecimal("100"), MathContext.DECIMAL64);
            return new BigDecimal(baseAmount.toString()).multiply(ratioPercentage).setScale(0, RoundingMode.HALF_UP).intValue();
        }
        if (CalculateModeEnum.FIXED.equals(profitParam.getCalculateMode())) {
            return profitParam.getProfitRatio();
        }
        return 0;
    }


    /**
     * 如果可能，发送会员段位升级事件
     *
     * @param member  *
     * @param account *
     */
    private void publishRankUpgradeEventIfPossible(Member member, Account account) {
        RankParam rankParam = this.getRankParam(member.getRankType());
        String platformUsername = CurrentPlatformHelper.username();
        if (rankParam == null) {
            if (log.isDebugEnabled()) {
                log.debug("没有合适的段位参数 {},{}", platformUsername, member.getRankType().getCode());
            }
            return;
        }
        // 判断当前分数是否在该段位参数区间
        Integer pointsTotalHistory = account.getPointsTotalHistory();
        if (rankParam.getBeginPoints() >= pointsTotalHistory && rankParam.getEndPoints() <= pointsTotalHistory) {
            if (log.isDebugEnabled()) {
                log.debug("发布会员升级事件 {},{}", platformUsername, member.getUsername());
            }
            UpgradeEvent.Source source = new UpgradeEvent.Source();
            source.setMemberId(member.getId());
            applicationEventPublisher.publishEvent(new UpgradeEvent(ProfitTypeEnum.UPGRADE, source));
        }

    }


    /**
     * 处理分润
     * 查询分润参数（包括佣金和积分）
     * 进行分润（如果存在对应分润参数）
     */
    protected void handleProfit(ProfitEvent inviteEvent, Member member, ProfitTypeEnum pte, ProfitLevelEnum ple) {
        // 查询分润参数
        ProfitParam moneyParam = this.getProfitParam(AccountTypeEnum.MONEY, pte, ple, member.getIdentityType(), member.getRankType());
        ProfitParam pointsParam = this.getProfitParam(AccountTypeEnum.POINTS, pte, ple, member.getIdentityType(), member.getRankType());
        // 检查分润参数是否存在
        if (Objects.nonNull(moneyParam) || Objects.nonNull(pointsParam)) {
            // 进行分润
            this.doProfit(inviteEvent, member, moneyParam, pointsParam);
        } else {
            if (log.isDebugEnabled()) {
                log.debug("没有合适的分润参数，跳过分润。（{}，{}，{}，{}，{}，{}）", member.getPlatformUsername(), member.getUsername(), pte.getCode(), ple.getCode(), member.getIdentityType(), member.getRankType());
            }
        }
    }


    /**
     * 执行分润逻辑
     * 计算佣金/积分数量，并入对应会员帐户
     */
    @Transactional
    public void doProfit(ProfitEvent event, Member member, ProfitParam moneyParam, ProfitParam pointsParam) {
        String platformUsername = CurrentPlatformHelper.username();
        log.info("开始执行分润,人员为{},{}", member.getUsername(), platformUsername);
        Account account = crudAccountService.selectByMemberId(member.getId());

        // 计算佣金分润参数的钱
        if (moneyParam != null) {
            Integer profitAmount = this.calculateProfitAmount(moneyParam, event.getEventAmount());
            // 大于0才有保存的意义
            if (profitAmount > 0) {
                // 保存佣金分销记录
                ProfitRecord profitRecord = new ProfitRecord();
                profitRecord.setProfitEventId(event.getId());
                profitRecord.setAccountType(AccountTypeEnum.MONEY);
                profitRecord.setImpactMemberId(member.getId());
                profitRecord.setIncomeCount(profitAmount);
                profitRecord.setMemo(String.format("%s,获得佣金收入%s元", member.getNickname(), DsUtil.fenToYuan(profitAmount)));
                profitRecord = crudProfitRecordService.insert(profitRecord);

                // 保存佣金入账记录
                AccountRecord accountRecord = new AccountRecord();
                accountRecord.setMemberId(member.getId());
                accountRecord.setAccountId(account.getId());
                accountRecord.setAccountType(AccountTypeEnum.MONEY);
                accountRecord.setBeforeChangeTotal(account.getMoneyTotal());
                accountRecord.setAfterChangeTotal(account.getMoneyTotal() + profitAmount);
                accountRecord.setChangeAmount(profitAmount);
                accountRecord.setChangeType(ChangeTypeEnum.PROFIT);
                accountRecord.setChangeRecordId(profitRecord.getId());
                crudAccountRecordService.insert(accountRecord);

                // 更新账户
                account.setMoneyTotalHistory(account.getMoneyTotalHistory() + profitAmount);
                account.setMoneyTotal(account.getMoneyTotal() + profitAmount);
                account.setMoneyAvailable(account.getMoneyAvailable() + profitAmount);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("佣金收益为0, 参数[{}],基础金额[{}]", moneyParam, event.getEventAmount());
                }
            }
        }

        // 计算积分分润参数的积分
        boolean isValidPointsParam = false;
        if (pointsParam != null) {
            Integer profitAmount = this.calculateProfitAmount(pointsParam, event.getEventAmount());
            // 大于0才有保存的意义
            if (profitAmount > 0) {

                // 设置标志为rue
                isValidPointsParam = true;

                // 保存佣金分销记录
                ProfitRecord profitRecord = new ProfitRecord();
                profitRecord.setProfitEventId(event.getId());
                profitRecord.setAccountType(AccountTypeEnum.POINTS);
                profitRecord.setImpactMemberId(member.getId());
                profitRecord.setIncomeCount(profitAmount);
                profitRecord.setMemo(String.format("%s,获得积分收入%s元", member.getNickname(), DsUtil.fenToYuan(profitAmount)));
                profitRecord = crudProfitRecordService.insert(profitRecord);

                // 保存佣金入账记录
                AccountRecord accountRecord = new AccountRecord();
                accountRecord.setMemberId(member.getId());
                accountRecord.setAccountId(account.getId());
                accountRecord.setAccountType(AccountTypeEnum.POINTS);
                accountRecord.setBeforeChangeTotal(account.getPointsTotal());
                accountRecord.setAfterChangeTotal(account.getPointsTotal() + profitAmount);
                accountRecord.setChangeAmount(profitAmount);
                accountRecord.setChangeType(ChangeTypeEnum.PROFIT);
                accountRecord.setChangeRecordId(profitRecord.getId());
                crudAccountRecordService.insert(accountRecord);

                // 更新账户
                account.setPointsTotalHistory(account.getPointsTotalHistory() + profitAmount);
                account.setPointsTotal(account.getPointsTotal() + profitAmount);
                account.setPointsAvailable(account.getPointsAvailable() + profitAmount);
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("积分收益为0, 参数[{}],基础金额[{}]", moneyParam, event.getEventAmount());
                }
            }
        }

        // 更新账户
        crudAccountService.updateById(account);

        // 更新账户数据后，检查是否达到了段位升级事件
        if (isValidPointsParam) {
            this.publishRankUpgradeEventIfPossible(member, account);
        }

        // 日志
        log.info("执行分润结束,人员为{},{}", member.getUsername(), platformUsername);
    }


}
