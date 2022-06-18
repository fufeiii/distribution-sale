package cn.fufeii.ds.repository.crud;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.dao.MemberDao;
import cn.fufeii.ds.repository.entity.Member;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
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
    public Optional<Member> selectByIdOptional(Long id) {
        return Optional.ofNullable(memberDao.selectById(id));
    }

    /**
     * 通过ID获取一个存在的实体
     */
    public Member selectById(Long id) {
        return this.selectByIdOptional(id).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }

    /**
     * 通过条件获取一个可能存在的实体
     */
    public Optional<Member> selectOneOptional(Wrapper<Member> queryWrapper) {
        return Optional.ofNullable(memberDao.selectOne(queryWrapper));
    }

    /**
     * 通过条件获取一个存在的实体
     */
    public Member selectOne(Wrapper<Member> queryWrapper) {
        return this.selectOneOptional(queryWrapper).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
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
     * 插入实体
     */
    public Member insert(Member entity) {
        memberDao.insert(entity);
        return entity;
    }

    /**
     * 更新实体
     */
    public Member updateById(Member entity) {
        int row = memberDao.updateById(entity);
        if (row == 0) {
            throw new BizException(ExceptionEnum.ENTITY_UPDATE_FAIL);
        }
        return entity;
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


    public Optional<Member> selectByUsernameAndPlatformUsernameOptional(String username, String platformUsername) {
        LambdaQueryWrapper<Member> lambdaQueryWrapper = Wrappers.<Member>lambdaQuery()
                .eq(Member::getUsername, username)
                .eq(Member::getPlatformUsername, platformUsername);
        return this.selectOneOptional(lambdaQueryWrapper);
    }

    public Member selectByUsernameAndPlatformUsername(String username, String platformUsername) {
        return this.selectByUsernameAndPlatformUsernameOptional(username, platformUsername).orElseThrow(() -> new BizException(ExceptionEnum.ENTITY_NOT_EXIST));
    }


}
