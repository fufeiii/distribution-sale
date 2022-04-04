package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.repository.dao.ProfitRecordDao;
import cn.fufeii.ds.repository.entity.ProfitRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 分润记录
 * CRUD ProfitRecord Service
 *
 * @author FuFei
 */
@Service
public class CrudProfitRecordService {

    @Autowired
    private ProfitRecordDao profitRecordDao;

    /**
     * 列表查询
     */
    public List<ProfitRecord> selectList(Wrapper<ProfitRecord> queryWrapper) {
        return profitRecordDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<ProfitRecord> selectPage(Wrapper<ProfitRecord> queryWrapper, IPage<ProfitRecord> pageable) {
        return profitRecordDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<ProfitRecord> selectByIdOpt(Long id) {
        return Optional.ofNullable(profitRecordDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public ProfitRecord selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(RuntimeException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<ProfitRecord> selectOneOpt(Wrapper<ProfitRecord> queryWrapper) {
        return Optional.ofNullable(profitRecordDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public ProfitRecord selectOne(Wrapper<ProfitRecord> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(RuntimeException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<ProfitRecord> queryWrapper) {
        return profitRecordDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<ProfitRecord> queryWrapper) {
        return profitRecordDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public ProfitRecord insert(ProfitRecord entity) {
        profitRecordDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public ProfitRecord updateById(ProfitRecord entity) {
        profitRecordDao.updateById(entity);
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        profitRecordDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
