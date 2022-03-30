package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.repository.dao.SystemUserDao;
import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 系统用户
 * CRUD SystemUser Service
 *
 * @author FuFei
 */
@Service
public class CrudSystemUserService {

    @Autowired
    private SystemUserDao systemUserDao;

    /**
     * 列表查询
     */
    public List<SystemUser> selectList(Wrapper<SystemUser> queryWrapper) {
        return systemUserDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<SystemUser> selectPage(Wrapper<SystemUser> queryWrapper, IPage<SystemUser> pageable) {
        return systemUserDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<SystemUser> selectByIdOpt(Long id) {
        return Optional.ofNullable(systemUserDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public SystemUser selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(RuntimeException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<SystemUser> selectOneOpt(Wrapper<SystemUser> queryWrapper) {
        return Optional.ofNullable(systemUserDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public SystemUser selectOne(Wrapper<SystemUser> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(RuntimeException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<SystemUser> queryWrapper) {
        return systemUserDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<SystemUser> queryWrapper) {
        return systemUserDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入或者更新实体
     */
    public SystemUser insertOrUpdate(SystemUser entityParam) {
        if (entityParam.getId() == null) {
            systemUserDao.insert(entityParam);
        } else {
            systemUserDao.updateById(entityParam);
        }
        return entityParam;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        systemUserDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
