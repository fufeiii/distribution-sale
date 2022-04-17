package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.MemberRankConfigDao;
import cn.fufeii.ds.repository.entity.MemberRankConfig;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 段位配置
 * CRUD MemberRankConfig Service
 *
 * @author FuFei
 */
@Service
public class CrudMemberRankConfigService {

    @Autowired
    private MemberRankConfigDao MemberRankConfigDao;

    /**
     * 列表查询
     */
    public List<MemberRankConfig> selectList(Wrapper<MemberRankConfig> queryWrapper) {
        return MemberRankConfigDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<MemberRankConfig> selectPage(Wrapper<MemberRankConfig> queryWrapper, IPage<MemberRankConfig> pageable) {
        return MemberRankConfigDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<MemberRankConfig> selectByIdOptional(Long id) {
        return Optional.ofNullable(MemberRankConfigDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public MemberRankConfig selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(IllegalStateException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<MemberRankConfig> selectOneOptional(Wrapper<MemberRankConfig> queryWrapper) {
        return Optional.ofNullable(MemberRankConfigDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public MemberRankConfig selectOne(Wrapper<MemberRankConfig> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(IllegalStateException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<MemberRankConfig> queryWrapper) {
        return MemberRankConfigDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<MemberRankConfig> queryWrapper) {
        return MemberRankConfigDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public MemberRankConfig insert(MemberRankConfig entity) {
        MemberRankConfigDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public MemberRankConfig updateById(MemberRankConfig entity) {
        int row = MemberRankConfigDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.SERVER_SQL_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        MemberRankConfigDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
