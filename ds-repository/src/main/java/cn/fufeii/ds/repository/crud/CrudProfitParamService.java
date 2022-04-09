package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
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
    public Optional<ProfitParam> selectByIdOptional(Long id) {
        return Optional.ofNullable(profitParamDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public ProfitParam selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST, "id(" + id + ")"));
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<ProfitParam> selectOneOptional(Wrapper<ProfitParam> queryWrapper) {
        return Optional.ofNullable(profitParamDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public ProfitParam selectOne(Wrapper<ProfitParam> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(IllegalStateException::new);
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
     * 插入实体
     */
    public ProfitParam insert(ProfitParam entity) {
        profitParamDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public ProfitParam updateById(ProfitParam entity) {
        int row = profitParamDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.SERVER_SQL_UPDATE_FAIL);
        }
        return entity;
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
