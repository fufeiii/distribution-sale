package cn.fufeii.ds.server.service;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.constant.DsConstant;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.crud.CrudAccountService;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.entity.Account;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.model.api.request.MemberCreateRequest;
import cn.fufeii.ds.server.model.api.request.MemberIdentityTypeRequest;
import cn.fufeii.ds.server.model.api.response.MemberCreateResponse;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.InviteEvent;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * 会员 Service
 *
 * @author FuFei
 * @date 2022/3/19
 */
@Service
public class MemberService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private CrudMemberService crudMemberService;
    @Autowired
    private CrudAccountService crudAccountService;

    @GlobalLock(key = DsServerConstant.CURRENT_PLATFORM_USERNAME_SPEL + "#request.username")
    @Transactional
    public MemberCreateResponse create(MemberCreateRequest request) {
        Platform currentPlatform = CurrentPlatformHelper.self();
        String platformUsername = currentPlatform.getUsername();
        // 检查用户是否存在
        Optional<Member> memberOptional = crudMemberService.selectByUsernameOptional(request.getUsername(), platformUsername);
        if (memberOptional.isPresent()) {
            throw BizException.serverError(String.format("会员[%s]已存在", request.getUsername()));
        }
        // 如果被邀请加入的, 则检查邀请人
        boolean isJoinCreate = CharSequenceUtil.isNotBlank(request.getInviteUsername());
        Member inviterMember = null;
        if (isJoinCreate) {
            Optional<Member> inviteMemberOpt = crudMemberService.selectByUsernameOptional(request.getInviteUsername(), platformUsername);
            if (!inviteMemberOpt.isPresent()) {
                throw BizException.serverError(String.format("邀请人[%s]不存在", request.getInviteUsername()));
            }
            inviterMember = inviteMemberOpt.get();
        }

        // 创建用户
        Member inviteeMember = new Member();
        inviteeMember.setUsername(request.getUsername());
        inviteeMember.setNickname(request.getNickname());
        if (CharSequenceUtil.isBlank(request.getNickname())) {
            inviteeMember.setNickname(request.getUsername());
        }
        inviteeMember.setAvatar(StrUtil.isBlank(request.getAvatar()) ? CharSequenceUtil.EMPTY : request.getAvatar());
        inviteeMember.setFirstInviterId(DsConstant.NULL_MEMBER_INVITER_ID);
        inviteeMember.setSecondInviterId(DsConstant.NULL_MEMBER_INVITER_ID);
        inviteeMember.setThirdInviterId(DsConstant.NULL_MEMBER_INVITER_ID);
        if (isJoinCreate) {
            inviteeMember.setFirstInviterId(inviterMember.getId());
            Long secondInviterId = inviterMember.getFirstInviterId();
            if (!DsConstant.NULL_MEMBER_INVITER_ID.equals(secondInviterId)) {
                inviteeMember.setSecondInviterId(secondInviterId);
            }
            Long thirdInviterId = inviterMember.getSecondInviterId();
            if (!DsConstant.NULL_MEMBER_INVITER_ID.equals(thirdInviterId)) {
                inviteeMember.setThirdInviterId(thirdInviterId);
            }
        }
        inviteeMember.setIdentityType(MemberIdentityTypeEnum.GENERAL);
        inviteeMember.setRankType(MemberRankTypeEnum.BRONZE);
        inviteeMember.setState(StateEnum.ENABLE);
        inviteeMember.setPlatformUsername(platformUsername);
        inviteeMember.setPlatformNickname(currentPlatform.getNickname());
        crudMemberService.insert(inviteeMember);
        Long memberId = inviteeMember.getId();

        // 创建账户
        Account account = new Account();
        account.setMemberId(memberId);
        account.setMoneyTotalHistory(0);
        account.setMoneyTotal(0);
        account.setMoneyAvailable(0);
        account.setMoneyFrozen(0);
        account.setPointsTotalHistory(0);
        account.setPointsTotal(0);
        account.setPointsAvailable(0);
        account.setPointsFrozen(0);
        crudAccountService.insert(account);

        // 发布邀请事件
        if (isJoinCreate) {
            InviteEvent.Source source = new InviteEvent.Source();
            source.setMemberId(memberId);
            applicationEventPublisher.publishEvent(new InviteEvent(ProfitTypeEnum.INVITE, source));
        }

        // 返回
        MemberCreateResponse response = new MemberCreateResponse();
        response.setMemberId(memberId);
        return response;
    }


    /**
     * 修改会员状态
     * 接口是幂等的, 目的是为了兼容管理后台的启禁用功能导致上游未同步更新,
     * 上游系统更改状态走逻辑调会ds系统, 此时ds系统兼容此情况
     *
     * @param username 会员标识
     * @param state    状态
     */
    @GlobalLock(key = DsServerConstant.CURRENT_PLATFORM_USERNAME_SPEL + "#username")
    public void changeState(String username, StateEnum state) {
        String platformUsername = CurrentPlatformHelper.username();
        Member member = crudMemberService.selectByUsername(username, platformUsername);
        if (state != member.getState()) {
            member.setState(state);
            crudMemberService.updateById(member);
        }
    }

    /**
     * 更新会员身份
     *
     * @param request *
     */
    @GlobalLock(key = DsServerConstant.CURRENT_PLATFORM_USERNAME_SPEL + "#request.username")
    public void identityType(MemberIdentityTypeRequest request) {
        Member member = crudMemberService.selectByUsername(request.getUsername(), CurrentPlatformHelper.username());
        // 有必要告诉上有系统, 提交重复了或者不正确的更新
        if (request.getIdentityType() == member.getIdentityType()) {
            throw new BizException(ExceptionEnum.BIZ_COMMON_ERROR, "重复更新会员身份");
        }
        member.setIdentityType(request.getIdentityType());
        crudMemberService.updateById(member);
    }


}
