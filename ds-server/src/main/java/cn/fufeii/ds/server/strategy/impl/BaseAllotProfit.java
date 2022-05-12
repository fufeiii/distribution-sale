package cn.fufeii.ds.server.strategy.impl;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.*;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.crud.*;
import cn.fufeii.ds.repository.entity.*;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.UpgradeEvent;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

/**
 * @author FuFei
 */
@Slf4j
public class BaseAllotProfit {
    @Autowired
    protected CrudMemberRankConfigService crudMemberRankConfigService;
    @Autowired
    protected CrudMemberService crudMemberService;
    @Autowired
    protected CrudAccountService crudAccountService;
    @Autowired
    protected CrudAllotProfitConfigService crudAllotProfitConfigService;
    @Autowired
    protected CrudAllotProfitEventService crudAllotProfitEventService;
    @Autowired
    protected CrudProfitIncomeRecordService crudProfitIncomeRecordService;
    @Autowired
    protected CrudAccountChangeRecordService crudAccountChangeRecordService;
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    /**
     * 获取分润配置
     */
    private AllotProfitConfig getProfitParam(AccountTypeEnum ate, ProfitTypeEnum pte, ProfitLevelEnum ple, MemberIdentityTypeEnum mite, MemberRankTypeEnum mre) {
        LambdaQueryWrapper<AllotProfitConfig> lambdaQueryWrapper = Wrappers.<AllotProfitConfig>lambdaQuery()
                .eq(AllotProfitConfig::getAccountType, ate)
                .eq(AllotProfitConfig::getProfitType, pte)
                .eq(AllotProfitConfig::getProfitLevel, ple)
                .eq(AllotProfitConfig::getMemberIdentityType, mite)
                .eq(AllotProfitConfig::getMemberRankType, mre)
                .eq(AllotProfitConfig::getPlatformUsername, CurrentPlatformHelper.username())
                .eq(AllotProfitConfig::getState, StateEnum.ENABLE);
        List<AllotProfitConfig> profitParamList = crudAllotProfitConfigService.selectList(lambdaQueryWrapper);
        if (profitParamList.size() > 1) {
            throw new BizException(ExceptionEnum.NO_SUITABLE_PARAM, ate.getMessage());
        }
        return profitParamList.isEmpty() ? null : profitParamList.get(0);
    }

    /**
     * 获取段位配置
     */
    private MemberRankConfig getRankParam(MemberRankTypeEnum mre) {
        LambdaQueryWrapper<MemberRankConfig> lambdaQueryWrapper = Wrappers.<MemberRankConfig>lambdaQuery()
                .eq(MemberRankConfig::getMemberRankType, mre)
                .eq(MemberRankConfig::getPlatformUsername, CurrentPlatformHelper.username())
                .eq(MemberRankConfig::getState, StateEnum.ENABLE);
        List<MemberRankConfig> rankParamList = crudMemberRankConfigService.selectList(lambdaQueryWrapper);
        if (rankParamList.size() > 1) {
            throw new BizException(ExceptionEnum.NO_SUITABLE_PARAM, "段位");
        }
        return rankParamList.isEmpty() ? null : rankParamList.get(0);
    }

    /**
     * 计算分润数量（四舍五入）
     * 如果是佣金, 计算单位为分
     *
     * @param profitParam *
     * @param baseAmount  基础金额
     */
    private Integer calculateProfitAmount(AllotProfitConfig profitParam, Integer baseAmount) {

        /*
         * 邀请分润和升级分润, 如果选择了百分比的计算模式, 那么基数就为1
         * 如果是交易分润的百分比计算模式, 那么基数就用交易的金额计算
         */
        if (DsServerConstant.DEFAULT_EVENT_AMOUNT == baseAmount) {
            baseAmount = 1;
        }

        // 只有是 百分比计算才需要进一步计算, 因为其他情况都时固定分润的范畴, 直接取分润比列就好了
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
     * 如果可能, 发送会员段位升级事件
     *
     * @param member  *
     * @param account *
     */
    private void publishRankUpgradeEventIfPossible(Member member, Account account) {
        MemberRankConfig rankParam = this.getRankParam(member.getRankType());
        String platformUsername = CurrentPlatformHelper.username();
        if (rankParam == null) {
            if (log.isDebugEnabled()) {
                log.debug("没有合适的段位配置 {},{}", member.getRankType().getCode(), platformUsername);
            }
            return;
        }
        // 判断当前分数是否在该段位配置区间
        Integer pointsTotalHistory = account.getPointsTotalHistory();
        if (rankParam.getBeginPoints() >= pointsTotalHistory && rankParam.getEndPoints() <= pointsTotalHistory) {
            if (log.isDebugEnabled()) {
                log.debug("发布会员升级事件 {},{}", member.getUsername(), platformUsername);
            }
            UpgradeEvent.Source source = new UpgradeEvent.Source();
            source.setMemberId(member.getId());
            applicationEventPublisher.publishEvent(new UpgradeEvent(ProfitTypeEnum.UPGRADE, source));
        }

    }

    /**
     * 为某个会员执行分润
     * 注意：不要抛出异常，因为当前会员分润中出现异常，不能让其他会员受到影响
     */
    protected void tryDoAllotProfit(AllotProfitEvent inviteEvent, Member member, ProfitTypeEnum pte, ProfitLevelEnum ple) {
        try {
            // 查询分润配置
            AllotProfitConfig moneyParam = this.getProfitParam(AccountTypeEnum.MONEY, pte, ple, member.getIdentityType(), member.getRankType());
            AllotProfitConfig pointsParam = this.getProfitParam(AccountTypeEnum.POINTS, pte, ple, member.getIdentityType(), member.getRankType());
            // 检查分润配置是否存在
            if (Objects.nonNull(moneyParam) || Objects.nonNull(pointsParam)) {
                // 进行分润，走AOP才能控制事务
                ((BaseAllotProfit) AopContext.currentProxy()).doAllotProfit(inviteEvent, member, moneyParam, pointsParam);
                return;
            }

            // 打印日志
            if (log.isDebugEnabled()) {
                log.debug("没有合适的分润配置, 跳过分润（{}, {}, {}, {}, {}, {}）", member.getUsername(), member.getPlatformUsername(), pte, ple, member.getIdentityType(), member.getRankType());
            }

        } catch (Exception e) {
            log.error("分润异常 {},{}", member.getUsername(), member.getPlatformUsername(), e);
        }

    }

    /**
     * 执行分润逻辑
     * 计算佣金/积分数量, 并入对应会员帐户
     */
    @GlobalLock(key = DsServerConstant.CPUS + "#member.username")
    @Transactional
    public void doAllotProfit(AllotProfitEvent event, Member member, AllotProfitConfig moneyParam, AllotProfitConfig pointsParam) {

        String platformUsername = CurrentPlatformHelper.username();
        log.info("开始执行分润,人员为{},{}", member.getUsername(), platformUsername);
        Account account = crudAccountService.selectByMemberId(member.getId());
        boolean accountChanged = false;

        // 计算佣金分润的钱
        if (moneyParam != null) {
            Integer profitAmount = this.calculateProfitAmount(moneyParam, event.getEventAmount());
            // 大于0才有保存的意义
            if (profitAmount > 0) {
                accountChanged = true;

                if (log.isDebugEnabled()) {
                    log.debug("增加佣金收益{}", profitAmount);
                }

                // 保存佣金分润记录
                ProfitIncomeRecord profitIncomeRecord = new ProfitIncomeRecord();
                profitIncomeRecord.setProfitEventId(event.getId());
                profitIncomeRecord.setAccountType(AccountTypeEnum.MONEY);
                profitIncomeRecord.setImpactMemberId(member.getId());
                profitIncomeRecord.setIncomeCount(profitAmount);
                profitIncomeRecord.setMemo(String.format("%s,获得佣金收入%s元", member.getNickname(), DsUtil.fenToYuan(profitAmount)));
                profitIncomeRecord = crudProfitIncomeRecordService.insert(profitIncomeRecord);

                // 保存佣金入账记录
                AccountChangeRecord accountChangeRecord = new AccountChangeRecord();
                accountChangeRecord.setMemberId(member.getId());
                accountChangeRecord.setAccountId(account.getId());
                accountChangeRecord.setAccountType(AccountTypeEnum.MONEY);
                accountChangeRecord.setBeforeAvailableCount(account.getMoneyAvailable());
                accountChangeRecord.setAfterAvailableCount(account.getMoneyAvailable() + profitAmount);
                accountChangeRecord.setChangeCount(profitAmount);
                accountChangeRecord.setChangeType(ChangeTypeEnum.PROFIT);
                accountChangeRecord.setChangeBizNumber(profitIncomeRecord.getId().toString());
                accountChangeRecord.setMemo("分润获得佣金");
                crudAccountChangeRecordService.insert(accountChangeRecord);

                // 更新账户
                account.setMoneyTotalHistory(account.getMoneyTotalHistory() + profitAmount);
                account.setMoneyTotal(account.getMoneyTotal() + profitAmount);
                account.setMoneyAvailable(account.getMoneyAvailable() + profitAmount);
            }
        }

        // 计算积分分润配置的积分
        boolean isValidPointsParam = false;
        if (pointsParam != null) {
            Integer profitAmount = this.calculateProfitAmount(pointsParam, event.getEventAmount());
            // 大于0才有保存的意义
            if (profitAmount > 0) {
                accountChanged = true;

                if (log.isDebugEnabled()) {
                    log.debug("增加积分收益{}", profitAmount);
                }

                // 设置标志为rue
                isValidPointsParam = true;

                // 保存佣金分润记录
                ProfitIncomeRecord profitRecord = new ProfitIncomeRecord();
                profitRecord.setProfitEventId(event.getId());
                profitRecord.setAccountType(AccountTypeEnum.POINTS);
                profitRecord.setImpactMemberId(member.getId());
                profitRecord.setIncomeCount(profitAmount);
                profitRecord.setMemo(String.format("%s,获得积分收入%s个", member.getNickname(), DsUtil.fenToYuan(profitAmount)));
                profitRecord = crudProfitIncomeRecordService.insert(profitRecord);

                // 保存佣金入账记录
                AccountChangeRecord accountChangeRecord = new AccountChangeRecord();
                accountChangeRecord.setMemberId(member.getId());
                accountChangeRecord.setAccountId(account.getId());
                accountChangeRecord.setAccountType(AccountTypeEnum.POINTS);
                accountChangeRecord.setBeforeAvailableCount(account.getPointsAvailable());
                accountChangeRecord.setAfterAvailableCount(account.getPointsAvailable() + profitAmount);
                accountChangeRecord.setChangeCount(profitAmount);
                accountChangeRecord.setChangeType(ChangeTypeEnum.PROFIT);
                // 这里设置为分润记录ID
                accountChangeRecord.setChangeBizNumber(profitRecord.getId().toString());
                accountChangeRecord.setMemo("分润获得积分");
                crudAccountChangeRecordService.insert(accountChangeRecord);

                // 更新账户
                account.setPointsTotalHistory(account.getPointsTotalHistory() + profitAmount);
                account.setPointsTotal(account.getPointsTotal() + profitAmount);
                account.setPointsAvailable(account.getPointsAvailable() + profitAmount);
            }
        }

        // 更新账户
        if (accountChanged) {
            crudAccountService.updateById(account);
        } else {
            if (log.isInfoEnabled()) {
                log.info("人员{}分润金额/积分均小于0 金钱参数[{}] 积分参数[{}]", member.getUsername(), moneyParam, pointsParam);
            }
        }

        // 更新账户数据后, 检查是否达到了段位升级事件
        if (accountChanged && isValidPointsParam) {
            this.publishRankUpgradeEventIfPossible(member, account);
        }

        // 日志
        log.info("执行分润结束,人员为{},{}", member.getUsername(), platformUsername);
    }

}
