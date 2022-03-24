package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.repository.dao.MemberDao;
import cn.fufeii.ds.repository.entity.Member;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * 会员信息
 * CRUD Member Service
 *
 * @author FuFei
 */
@Service
public class CrudMemberService {

    @Autowired
    private MemberDao memberDao;

    /**
     * 列表查询
     */
    public List<Member> selectList(Wrapper<Member> queryWrapper) {
        return memberDao.selectList(queryWrapper);
    }

    /**
     * 分页查询
     */
    public IPage<Member> selectPage(Wrapper<Member> queryWrapper, IPage<Member> pageable) {
        return memberDao.selectPage(pageable, queryWrapper);
    }

    /**
     * 通过ID获取一个可能存在的实体
     */
    public Optional<Member> selectByIdOpt(Long id) {
        return Optional.ofNullable(memberDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public Member selectById(Long id) {
        return this.selectByIdOpt(id).orElseThrow(RuntimeException::new);
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<Member> selectOneOpt(Wrapper<Member> queryWrapper) {
        return Optional.ofNullable(memberDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public Member selectOne(Wrapper<Member> queryWrapper) {
        return this.selectOneOpt(queryWrapper).orElseThrow(RuntimeException::new);
    }

    /**
     * 统计个数
     */
    public long count(Wrapper<Member> queryWrapper) {
        return memberDao.selectCount(queryWrapper);
    }

    /**
     * 是否存在
     */
    public boolean exist(Wrapper<Member> queryWrapper) {
        return memberDao.selectCount(queryWrapper) > 0;
    }

    /**
     * 插入或者更新实体
     */
    public Member insertOrUpdate(Member entityParam) {
        if (entityParam.getId() == null) {
            memberDao.insert(entityParam);
        } else {
            memberDao.updateById(entityParam);
        }
        return entityParam;
    }

    /**
     * 删除一个实体
     */
    public void deleteById(Long id) {
        memberDao.deleteById(id);
    }


    // --------------------------------------------------------------------------------- //
    // ---------------------------- 下面基础CRUD的扩展 ----------------------------------- //
    // --------------------------------------------------------------------------------- //


    public Optional<Member> selectByUsernameOpt(String username) {
        return this.selectOneOpt(Wrappers.<Member>lambdaQuery().eq(Member::getUsername, username));
    }

    public boolean existByUsername(String username) {
        Long count = memberDao.selectCount(Wrappers.<Member>lambdaQuery().eq(Member::getUsername, username));
        return count > 0;
    }

}
