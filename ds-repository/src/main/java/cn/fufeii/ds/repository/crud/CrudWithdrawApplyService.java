package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.repository.dao.WithdrawApplyDao;
import cn.fufeii.ds.repository.entity.WithdrawApply;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 提现申请
 * CRUD WithdrawApply Service
 *
 * @author FuFei
 */
@Service
public class CrudWithdrawApplyService {

    @Autowired
    private WithdrawApplyDao withdrawApplyDao;

    /**
     * 列表查询
     */
    public List<WithdrawApply> selectList(Wrapper<WithdrawApply> queryWrapper) {
        return withdrawApplyDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<WithdrawApply> selectPage(Wrapper<WithdrawApply> queryWrapper, IPage<WithdrawApply> pageable) {
        return withdrawApplyDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<WithdrawApply> selectByIdOpt(Long id) {
        return Optional.ofNullable(withdrawApplyDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public WithdrawApply selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(RuntimeException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<WithdrawApply> selectOneOpt(Wrapper<WithdrawApply> queryWrapper) {
        return Optional.ofNullable(withdrawApplyDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public WithdrawApply selectOne(Wrapper<WithdrawApply> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(RuntimeException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<WithdrawApply> queryWrapper) {
        return withdrawApplyDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<WithdrawApply> queryWrapper) {
        return withdrawApplyDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public WithdrawApply insert(WithdrawApply entity) {
        withdrawApplyDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public WithdrawApply updateById(WithdrawApply entity) {
        withdrawApplyDao.updateById(entity);
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        withdrawApplyDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
