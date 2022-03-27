package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.repository.dao.SysUserDao;
import cn.fufeii.ds.repository.entity.SysUser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 系统用户
 * CRUD SysUser Service
 *
 * @author FuFei
 */
@Service
public class CrudSysUserService {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 列表查询
     */
    public List<SysUser> selectList(Wrapper<SysUser> queryWrapper) {
        return sysUserDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<SysUser> selectPage(Wrapper<SysUser> queryWrapper, IPage<SysUser> pageable) {
        return sysUserDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<SysUser> selectByIdOpt(Long id) {
        return Optional.ofNullable(sysUserDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public SysUser selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(RuntimeException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<SysUser> selectOneOpt(Wrapper<SysUser> queryWrapper) {
        return Optional.ofNullable(sysUserDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public SysUser selectOne(Wrapper<SysUser> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(RuntimeException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<SysUser> queryWrapper) {
        return sysUserDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<SysUser> queryWrapper) {
        return sysUserDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入或者更新实体
     */
    public SysUser insertOrUpdate(SysUser entityParam) {
        if (entityParam.getId() == null) {
            sysUserDao.insert(entityParam);
        } else {
            sysUserDao.updateById(entityParam);
        }
        return entityParam;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        sysUserDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
