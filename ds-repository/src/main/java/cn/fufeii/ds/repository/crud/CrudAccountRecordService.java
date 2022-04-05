package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.AccountRecordDao;
import cn.fufeii.ds.repository.entity.AccountRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 账户记录
 * CRUD AccountRecord Service
 *
 * @author FuFei
 */
@Service
public class CrudAccountRecordService {

    @Autowired
    private AccountRecordDao accountRecordDao;

    /**
     * 列表查询
     */
    public List<AccountRecord> selectList(Wrapper<AccountRecord> queryWrapper) {
        return accountRecordDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<AccountRecord> selectPage(Wrapper<AccountRecord> queryWrapper, IPage<AccountRecord> pageable) {
        return accountRecordDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<AccountRecord> selectByIdOpt(Long id) {
        return Optional.ofNullable(accountRecordDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public AccountRecord selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(IllegalStateException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<AccountRecord> selectOneOpt(Wrapper<AccountRecord> queryWrapper) {
        return Optional.ofNullable(accountRecordDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public AccountRecord selectOne(Wrapper<AccountRecord> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(IllegalStateException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<AccountRecord> queryWrapper) {
        return accountRecordDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<AccountRecord> queryWrapper) {
        return accountRecordDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public AccountRecord insert(AccountRecord entity) {
        accountRecordDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public AccountRecord updateById(AccountRecord entity) {
        int row = accountRecordDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.SERVER_SQL_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        accountRecordDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
