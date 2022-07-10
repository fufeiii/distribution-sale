package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.AllotProfitEventDao;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 分润事件
 * CRUD AllotProfitEvent Service
 *
 * @author FuFei
 */
@Service
public class CrudAllotProfitEventService {

    @Autowired
    private AllotProfitEventDao AllotProfitEventDao;

    /**
     * 列表查询
     */
    public List<AllotProfitEvent> selectList(Wrapper<AllotProfitEvent> queryWrapper) {
        return AllotProfitEventDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<AllotProfitEvent> selectPage(Wrapper<AllotProfitEvent> queryWrapper, IPage<AllotProfitEvent> pageable) {
        return AllotProfitEventDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<AllotProfitEvent> selectByIdOptional(Long id) {
        return Optional.ofNullable(AllotProfitEventDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public AllotProfitEvent selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<AllotProfitEvent> selectOneOptional(Wrapper<AllotProfitEvent> queryWrapper) {
        return Optional.ofNullable(AllotProfitEventDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public AllotProfitEvent selectOne(Wrapper<AllotProfitEvent> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<AllotProfitEvent> queryWrapper) {
        return AllotProfitEventDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<AllotProfitEvent> queryWrapper) {
        return AllotProfitEventDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public AllotProfitEvent insert(AllotProfitEvent entity) {
        AllotProfitEventDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public AllotProfitEvent updateById(AllotProfitEvent entity) {
        int row = AllotProfitEventDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.ENTITY_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        AllotProfitEventDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //

}
