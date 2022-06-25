package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.ProfitIncomeRecordDao;
import cn.fufeii.ds.repository.entity.ProfitIncomeRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 分润记录
 * CRUD ProfitIncomeRecord Service
 *
 * @author FuFei
 */
@Service
public class CrudProfitIncomeRecordService {

    @Autowired
    private ProfitIncomeRecordDao ProfitIncomeRecordDao;

    /**
     * 列表查询
     */
    public List<ProfitIncomeRecord> selectList(Wrapper<ProfitIncomeRecord> queryWrapper) {
        return ProfitIncomeRecordDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<ProfitIncomeRecord> selectPage(Wrapper<ProfitIncomeRecord> queryWrapper, IPage<ProfitIncomeRecord> pageable) {
        return ProfitIncomeRecordDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<ProfitIncomeRecord> selectByIdOptional(Long id) {
        return Optional.ofNullable(ProfitIncomeRecordDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public ProfitIncomeRecord selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<ProfitIncomeRecord> selectOneOptional(Wrapper<ProfitIncomeRecord> queryWrapper) {
        return Optional.ofNullable(ProfitIncomeRecordDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public ProfitIncomeRecord selectOne(Wrapper<ProfitIncomeRecord> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<ProfitIncomeRecord> queryWrapper) {
        return ProfitIncomeRecordDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<ProfitIncomeRecord> queryWrapper) {
        return ProfitIncomeRecordDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public ProfitIncomeRecord insert(ProfitIncomeRecord entity) {
        ProfitIncomeRecordDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public ProfitIncomeRecord updateById(ProfitIncomeRecord entity) {
        int row = ProfitIncomeRecordDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.ENTITY_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        ProfitIncomeRecordDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


    public List<ProfitIncomeRecord> selectByProfitEventId(Long eventId) {
        LambdaQueryWrapper<ProfitIncomeRecord> lambdaQueryWrapper = Wrappers.<ProfitIncomeRecord>lambdaQuery().eq(ProfitIncomeRecord::getProfitEventId, eventId);
        return ProfitIncomeRecordDao.selectList(lambdaQueryWrapper);
    }

}
