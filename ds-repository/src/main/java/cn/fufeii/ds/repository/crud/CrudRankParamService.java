package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.RankParamDao;
import cn.fufeii.ds.repository.entity.RankParam;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 段位配置
 * CRUD RankParam Service
 *
 * @author FuFei
 */
@Service
public class CrudRankParamService {

    @Autowired
    private RankParamDao rankParamDao;

    /**
     * 列表查询
     */
    public List<RankParam> selectList(Wrapper<RankParam> queryWrapper) {
        return rankParamDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<RankParam> selectPage(Wrapper<RankParam> queryWrapper, IPage<RankParam> pageable) {
        return rankParamDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<RankParam> selectByIdOpt(Long id) {
        return Optional.ofNullable(rankParamDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public RankParam selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(IllegalStateException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<RankParam> selectOneOpt(Wrapper<RankParam> queryWrapper) {
        return Optional.ofNullable(rankParamDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public RankParam selectOne(Wrapper<RankParam> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(IllegalStateException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<RankParam> queryWrapper) {
        return rankParamDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<RankParam> queryWrapper) {
        return rankParamDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入实体
     */
    public RankParam insert(RankParam entity) {
        rankParamDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public RankParam updateById(RankParam entity) {
        int row = rankParamDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.SERVER_SQL_UPDATE_FAIL);
        }
        return entity;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        rankParamDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


}
