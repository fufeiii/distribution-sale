package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.MemberQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.MemberAccountResponse;
import cn.fufeii.ds.admin.model.vo.response.MemberResponse;
import cn.fufeii.ds.common.constant.DsConstant;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.repository.crud.CrudAccountService;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.entity.Account;
import cn.fufeii.ds.repository.entity.Member;
import cn.hutool.core.text.StrPool;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 会员信息 Service
 *
 * @author FuFei
 */
@Service
public class MemberService {

    @Autowired
    private CrudMemberService crudMemberService;
    @Autowired
    private CrudAccountService crudAccountService;

    /**
     * 分页查询
     */
    public IPage<MemberResponse> page(MemberQueryRequest pageParam, IPage<Member> pageable) {
        LambdaQueryWrapper<Member> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, Member.class));
        IPage<Member> selectPage = crudMemberService.selectPage(queryWrapper, pageable);
        // 查询不到数据直接返回
        if (selectPage.getRecords().isEmpty()) {
            return Page.<MemberResponse>of(selectPage.getCurrent(), selectPage.getSize(), selectPage.getTotal()).setRecords(Collections.emptyList());
        }

        // 查询父级会员信息
        Set<Long> inviterIdSet = selectPage.getRecords().stream()
                .flatMap(it -> Stream.of(it.getFirstInviterId(), it.getSecondInviterId(), it.getThirdInviterId()))
                .filter(it -> !DsConstant.NULL_MEMBER_INVITER_ID.equals(it))
                .collect(Collectors.toSet());

        // 邀请人Map
        Map<Long, Member> inviterMapTemp = Collections.emptyMap();
        if (!inviterIdSet.isEmpty()) {
            inviterMapTemp = crudMemberService.selectList(Wrappers.<Member>lambdaQuery().in(Member::getId, inviterIdSet))
                    .stream().collect(Collectors.toMap(Member::getId, Function.identity()));
        }
        Map<Long, Member> inviterMap = inviterMapTemp;
        // 组装response对象返回
        return selectPage.convert(it -> {
            MemberResponse response = new MemberResponse();
            response.setId(it.getId());
            response.setPlatformUsername(it.getPlatformUsername());
            response.setPlatformNickname(it.getPlatformNickname());
            response.setUsername(it.getUsername());
            response.setNickname(it.getNickname());
            response.setAvatar(it.getAvatar());
            response.setIdentityType(it.getIdentityType().getMessage());
            response.setRankType(it.getRankType().getMessage());
            response.setState(it.getState().getMessage());
            response.setCreateDateTime(it.getCreateDateTime());
            // 设置上级信息
            Optional<Member> firstInviterOpt = Optional.ofNullable(inviterMap.get(it.getFirstInviterId()));
            response.setFirstInviterUsername(firstInviterOpt.map(Member::getUsername).orElse(StrPool.DASHED));
            response.setFirstInviterNickname(firstInviterOpt.map(Member::getNickname).orElse(StrPool.DASHED));
            Optional<Member> secondInviterOpt = Optional.ofNullable(inviterMap.get(it.getSecondInviterId()));
            response.setSecondInviterUsername(secondInviterOpt.map(Member::getUsername).orElse(StrPool.DASHED));
            response.setSecondInviterNickname(secondInviterOpt.map(Member::getNickname).orElse(StrPool.DASHED));
            Optional<Member> thirdInviterOpt = Optional.ofNullable(inviterMap.get(it.getThirdInviterId()));
            response.setThirdInviterUsername(thirdInviterOpt.map(Member::getUsername).orElse(StrPool.DASHED));
            response.setThirdInviterNickname(thirdInviterOpt.map(Member::getNickname).orElse(StrPool.DASHED));
            return response;
        });
    }

    /**
     * 会员账户信息
     */
    public MemberAccountResponse account(Long memberId) {
        Member member = crudMemberService.selectById(memberId);
        Account account = crudAccountService.selectByMemberId(memberId);

        // 组装响应
        MemberAccountResponse response = new MemberAccountResponse();
        response.setId(account.getId());
        response.setMemberId(memberId);
        response.setMemberUsername(member.getUsername());
        response.setMemberNickname(member.getNickname());
        response.setMemberState(member.getState().getMessage());
        response.setMoneyTotalHistory(DsUtil.fenToYuan(account.getMoneyTotalHistory()));
        response.setMoneyTotal(DsUtil.fenToYuan(account.getMoneyTotal()));
        response.setMoneyAvailable(DsUtil.fenToYuan(account.getMoneyAvailable()));
        response.setMoneyFrozen(DsUtil.fenToYuan(account.getMoneyFrozen()));
        response.setPointsTotalHistory(account.getPointsTotalHistory().toString());
        response.setPointsTotal(account.getPointsTotal().toString());
        response.setPointsAvailable(account.getPointsAvailable().toString());
        response.setPointsFrozen(account.getPointsFrozen().toString());
        return response;
    }


    /**
     * 修改状态
     */
    public void changeState(Long id, StateEnum stateEnum) {
        Member member = crudMemberService.selectById(id);
        if (stateEnum == member.getState()) {
            throw new BizException(ExceptionEnum.UPDATE_STATE_REPEATEDLY);
        }
        member.setState(stateEnum);
        crudMemberService.updateById(member);
    }


}