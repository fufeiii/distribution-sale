package cn.fufeii.ds.server.service;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.ChangeTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.enumerate.biz.WithdrawStateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.crud.CrudAccountRecordService;
import cn.fufeii.ds.repository.crud.CrudAccountService;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.crud.CrudWithdrawApplyService;
import cn.fufeii.ds.repository.entity.*;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.model.api.request.WithdrawApplyRequest;
import cn.fufeii.ds.server.model.api.response.WithdrawApplyResponse;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * WithdrawService
 *
 * @author FuFei
 * @date 2022/4/10
 */
@Service
public class WithdrawService {
    @Autowired
    private CrudMemberService crudMemberService;
    @Autowired
    private CrudAccountService crudAccountService;
    @Autowired
    private CrudAccountRecordService crudAccountRecordService;
    @Autowired
    private CrudWithdrawApplyService crudWithdrawApplyService;

    /**
     * 创建提现申请
     *
     * @param request *
     */
    @Transactional
    @GlobalLock(key = DsServerConstant.CURRENT_PLATFORM_USERNAME_SPEL + "#request.withdrawNumber")
    public WithdrawApplyResponse create(WithdrawApplyRequest request) {
        Platform platform = CurrentPlatformHelper.self();
        // 判断会员状态
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(request.getWithdrawNumber(), platform.getUsername());
        if (StateEnum.DISABLE == member.getState()) {
            throw new BizException(ExceptionEnum.MEMBER_DISABLED, request.getMemberUsername());
        }
        // 判断单号唯一性
        LambdaQueryWrapper<WithdrawApply> lambdaQueryWrapper = Wrappers.<WithdrawApply>lambdaQuery()
                .eq(WithdrawApply::getWithdrawNumber, request.getWithdrawNumber())
                .eq(WithdrawApply::getPlatformUsername, platform.getUsername());
        if (crudWithdrawApplyService.exist(lambdaQueryWrapper)) {
            throw new BizException(ExceptionEnum.BIZ_COMMON_ERROR, "提现单号" + request.getWithdrawNumber() + "已存在");
        }

        // 检查账户
        Account account = crudAccountService.selectByMemberId(member.getId());
        int balanceAmount = account.getMoneyAvailable() - request.getWithdrawAmount();
        if (balanceAmount < 0) {
            throw new BizException(ExceptionEnum.BIZ_COMMON_ERROR, "余额不足");
        }

        // 创建提现申请
        WithdrawApply withdrawApply = new WithdrawApply();
        withdrawApply.setPlatformUsername(platform.getUsername());
        withdrawApply.setPlatformNickname(platform.getNickname());
        withdrawApply.setMemberId(member.getId());
        withdrawApply.setMemberUsername(member.getUsername());
        withdrawApply.setWithdrawNumber(request.getWithdrawNumber());
        withdrawApply.setWithdrawAmount(request.getWithdrawAmount());
        // FIXME 费率计算
        withdrawApply.setFeeAmount(0);
        withdrawApply.setWithdrawState(WithdrawStateEnum.WAIT);
        withdrawApply.setWithdrawDesc(request.getWithdrawDesc());
        withdrawApply.setApprovalTime(null);
        withdrawApply.setApprovalDesc(null);
        withdrawApply = crudWithdrawApplyService.insert(withdrawApply);

        // 账户变动记录
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setMemberId(member.getId());
        accountRecord.setAccountId(account.getId());
        accountRecord.setAccountType(AccountTypeEnum.MONEY);
        accountRecord.setBeforeChangeTotal(account.getMoneyTotal());
        accountRecord.setAfterChangeTotal(account.getMoneyTotal());
        accountRecord.setChangeAmount(0);
        accountRecord.setChangeType(ChangeTypeEnum.WITHDRAW_FREEZE);
        accountRecord.setChangeRecordId(withdrawApply.getId());
        crudAccountRecordService.insert(accountRecord);

        // 预扣提现金额
        account.setMoneyAvailable(balanceAmount);
        account.setMoneyFrozen(account.getMoneyFrozen() + request.getWithdrawAmount());
        crudAccountService.updateById(account);

        WithdrawApplyResponse response = new WithdrawApplyResponse();
        response.setWithdrawId(withdrawApply.getId());
        return response;
    }


}
