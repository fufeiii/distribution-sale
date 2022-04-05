package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.ProfitEventDao;
import cn.fufeii.ds.repository.entity.ProfitEvent;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 分润事件
 * CRUD ProfitEvent Service
 *
 * @author FuFei
 */
@Service
public class CrudProfitEventService {

    @Autowired
    private ProfitEventDao profitEventDao;

    /**
     * 列表查询
     */
    public List<ProfitEvent> selectList(Wrapper<ProfitEvent> queryWrapper) {
        return profitEventDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<ProfitEvent> selectPage(Wrapper<ProfitEvent> queryWrapper, IPage<ProfitEvent> pageable) {
        return profitEventDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<ProfitEvent> selectByIdOptional(Long id) {
        return Optional.ofNullable(profitEventDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public ProfitEvent selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(IllegalStateException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<ProfitEvent> selectOneOpt(Wrapper<ProfitEvent> queryWrapper) {
        return Optional.ofNullable(profitEventDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public ProfitEvent selectOne(Wrapper<ProfitEvent> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(IllegalStateException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<ProfitEvent> queryWrapper) {
        return profitEventDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<ProfitEvent> queryWrapper) {
        return profitEventDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public ProfitEvent insert(ProfitEvent entity) {
        profitEventDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public ProfitEvent updateById(ProfitEvent entity) {
        int row = profitEventDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.SERVER_SQL_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        profitEventDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
