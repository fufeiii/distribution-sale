package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.repository.dao.ProfitParamDao;
import cn.fufeii.ds.repository.entity.ProfitParam;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 分润参数
 * CRUD ProfitParam Service
 *
 * @author FuFei
 */
@Service
public class CrudProfitParamService {

    @Autowired
    private ProfitParamDao profitParamDao;

    /**
     * 列表查询
     */
    public List<ProfitParam> selectList(Wrapper<ProfitParam> queryWrapper) {
        return profitParamDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<ProfitParam> selectPage(Wrapper<ProfitParam> queryWrapper, IPage<ProfitParam> pageable) {
        return profitParamDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<ProfitParam> selectByIdOpt(Long id) {
        return Optional.ofNullable(profitParamDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public ProfitParam selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(RuntimeException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<ProfitParam> selectOneOpt(Wrapper<ProfitParam> queryWrapper) {
        return Optional.ofNullable(profitParamDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public ProfitParam selectOne(Wrapper<ProfitParam> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(RuntimeException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<ProfitParam> queryWrapper) {
        return profitParamDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<ProfitParam> queryWrapper) {
        return profitParamDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入或者更新实体
     */
    public ProfitParam insertOrUpdate(ProfitParam entityParam) {
        if (entityParam.getId() == null) {
            profitParamDao.insert(entityParam);
        } else {
            profitParamDao.updateById(entityParam);
        }
        return entityParam;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        profitParamDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
