package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.AccountChangeRecordDao;
import cn.fufeii.ds.repository.entity.AccountChangeRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 账户记录
 * CRUD AccountChangeRecord Service
 *
 * @author FuFei
 */
@Service
public class CrudAccountChangeRecordService {

    @Autowired
    private AccountChangeRecordDao accountChangeRecordDao;

    /**
     * 列表查询
     */
    public List<AccountChangeRecord> selectList(Wrapper<AccountChangeRecord> queryWrapper) {
        return accountChangeRecordDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<AccountChangeRecord> selectPage(Wrapper<AccountChangeRecord> queryWrapper, IPage<AccountChangeRecord> pageable) {
        return accountChangeRecordDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<AccountChangeRecord> selectByIdOptional(Long id) {
        return Optional.ofNullable(accountChangeRecordDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public AccountChangeRecord selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(IllegalStateException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<AccountChangeRecord> selectOneOptional(Wrapper<AccountChangeRecord> queryWrapper) {
        return Optional.ofNullable(accountChangeRecordDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public AccountChangeRecord selectOne(Wrapper<AccountChangeRecord> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(IllegalStateException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<AccountChangeRecord> queryWrapper) {
        return accountChangeRecordDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<AccountChangeRecord> queryWrapper) {
        return accountChangeRecordDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public AccountChangeRecord insert(AccountChangeRecord entity) {
        accountChangeRecordDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public AccountChangeRecord updateById(AccountChangeRecord entity) {
        int row = accountChangeRecordDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.SERVER_SQL_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        accountChangeRecordDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
