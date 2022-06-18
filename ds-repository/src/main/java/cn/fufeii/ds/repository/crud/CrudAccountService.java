package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.AccountDao;
import cn.fufeii.ds.repository.entity.Account;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 会员账户
 * CRUD Account Service
 *
 * @author FuFei
 */
@Service
public class CrudAccountService {

    @Autowired
    private AccountDao accountDao;

    /**
     * 列表查询
     */
    public List<Account> selectList(Wrapper<Account> queryWrapper) {
        return accountDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<Account> selectPage(Wrapper<Account> queryWrapper, IPage<Account> pageable) {
        return accountDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<Account> selectByIdOptional(Long id) {
        return Optional.ofNullable(accountDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public Account selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<Account> selectOneOptional(Wrapper<Account> queryWrapper) {
        return Optional.ofNullable(accountDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public Account selectOne(Wrapper<Account> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<Account> queryWrapper) {
        return accountDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<Account> queryWrapper) {
        return accountDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public Account insert(Account entity) {
        accountDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public Account updateById(Account entity) {
        int row = accountDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.ENTITY_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        accountDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //

    /**
     * 通过会员主键查询账户
     */
    public Account selectByMemberId(Long memberId) {
        return this.selectOne(Wrappers.<Account>lambdaQuery().eq(Account::getMemberId, memberId));
    }

}
