package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.MemberRequest;
import cn.fufeii.ds.admin.model.vo.response.MemberResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.entity.Member;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 会员信息 Service Impl
 * 业务实现
 *
 * @author FuFei
 */
@Service
public class MemberService {

    @Autowired
    private CrudMemberService crudMemberService;

    /**
     * 分页查询
     */
    public IPage<MemberResponse> page(MemberRequest pageParam, IPage<Member> pageable) {
        Wrapper<Member> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, Member.class));
        IPage<Member> selectPage = crudMemberService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            MemberResponse memberResponse = new MemberResponse();
            memberResponse.setId(it.getId());
            memberResponse.setAvatar(it.getAvatar());
            memberResponse.setUsername(it.getUsername());
            memberResponse.setNickname(it.getNickname());
            memberResponse.setIdentityType(it.getIdentityType().getMessage());
            memberResponse.setRankType(it.getRankType().getMessage());
            memberResponse.setState(it.getState().getMessage());
            return memberResponse;
        });
    }

    /**
     * 获取
     */
    public MemberResponse get(Long id) {
        Member member = crudMemberService.selectById(id);
        MemberResponse memberResp = new MemberResponse();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(member, memberResp);
        return memberResp;
    }


}