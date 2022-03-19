package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.MemberQueryRequest;
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
 * 会员信息 Service
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
    public IPage<MemberResponse> page(MemberQueryRequest pageParam, IPage<Member> pageable) {
        Wrapper<Member> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, Member.class));
        IPage<Member> selectPage = crudMemberService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            MemberResponse response = new MemberResponse();
            response.setId(it.getId());
            response.setAvatar(it.getAvatar());
            response.setUsername(it.getUsername());
            response.setNickname(it.getNickname());
            response.setFirParent(it.getFirParent());
            response.setSecParent(it.getSecParent());
            response.setThrParent(it.getThrParent());
            response.setIdentityType(it.getIdentityType().getMessage());
            response.setRankType(it.getRankType().getMessage());
            response.setState(it.getState().getMessage());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

}