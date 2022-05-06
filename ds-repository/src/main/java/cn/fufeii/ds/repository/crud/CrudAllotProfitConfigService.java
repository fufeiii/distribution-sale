package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.AllotProfitConfigDao;
import cn.fufeii.ds.repository.entity.AllotProfitConfig;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 分润配置
 * CRUD AllotProfitConfig Service
 *
 * @author FuFei
 */
@Service
public class CrudAllotProfitConfigService {

    @Autowired
    private AllotProfitConfigDao AllotProfitConfigDao;

    /**
     * 列表查询
     */
    public List<AllotProfitConfig> selectList(Wrapper<AllotProfitConfig> queryWrapper) {
        return AllotProfitConfigDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<AllotProfitConfig> selectPage(Wrapper<AllotProfitConfig> queryWrapper, IPage<AllotProfitConfig> pageable) {
        return AllotProfitConfigDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<AllotProfitConfig> selectByIdOptional(Long id) {
        return Optional.ofNullable(AllotProfitConfigDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public AllotProfitConfig selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST, "id(" + id + ")"));
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<AllotProfitConfig> selectOneOptional(Wrapper<AllotProfitConfig> queryWrapper) {
        return Optional.ofNullable(AllotProfitConfigDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public AllotProfitConfig selectOne(Wrapper<AllotProfitConfig> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(IllegalStateException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<AllotProfitConfig> queryWrapper) {
        return AllotProfitConfigDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<AllotProfitConfig> queryWrapper) {
        return AllotProfitConfigDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public AllotProfitConfig insert(AllotProfitConfig entity) {
        AllotProfitConfigDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public AllotProfitConfig updateById(AllotProfitConfig entity) {
        int row = AllotProfitConfigDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.SERVER_SQL_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        AllotProfitConfigDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
