package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.AccountQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.AccountResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudAccountService;
import cn.fufeii.ds.repository.entity.Account;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户管理 Service
 *
 * @author FuFei
 */
@Service
public class AccountService {

    @Autowired
    private CrudAccountService accountService;

    /**
     * 分页查询
     */
    public IPage<AccountResponse> page(AccountQueryRequest pageParam, IPage<Account> pageable) {
        Wrapper<Account> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, Account.class));
        IPage<Account> selectPage = accountService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            AccountResponse response = new AccountResponse();
            response.setId(it.getId());
            response.setMemberId(it.getMemberId());
            response.setMoneyTotal(it.getMoneyTotal());
            response.setMoneyAvailable(it.getMoneyAvailable());
            response.setMoneyFrozen(it.getMoneyFrozen());
            response.setPointsTotalHistory(it.getPointsTotalHistory());
            response.setPointsTotal(it.getPointsTotal());
            response.setPointsAvailable(it.getPointsAvailable());
            response.setPointsFrozen(it.getPointsFrozen());
            return response;
        });
    }

}