package cn.fufeii.ds.service.entity.impl;

import cn.fufeii.ds.repository.dao.AccountDao;
import cn.fufeii.ds.repository.entity.Account;
import cn.fufeii.ds.service.entity.BaseAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 会员账户 ServiceImpl
 *
 * @author FuFei
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountDao, Account> implements BaseAccountService {

}