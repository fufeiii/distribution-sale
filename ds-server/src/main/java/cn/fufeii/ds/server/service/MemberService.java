package cn.fufeii.ds.server.service;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.constant.DsConstant;
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
        // 检查用户是否存在
        if (crudMemberService.existByUsername(request.getUsername())) {
            throw BizException.serverError(String.format("会员[%s]已存在", request.getUsername()));
        }
        // 如果被邀请加入的，则检查邀请人
        boolean isJoinCreate = CharSequenceUtil.isNotBlank(request.getInviteUsername());
        Member inviteMember = null;
        if (isJoinCreate) {
            Optional<Member> inviteMemberOpt = crudMemberService.selectByUsernameOpt(request.getInviteUsername());
            if (!inviteMemberOpt.isPresent()) {
                throw BizException.serverError(String.format("邀请人[%s]不存在", request.getInviteUsername()));
            }
            inviteMember = inviteMemberOpt.get();
        }

        // 创建用户
        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setNickname(request.getNickname());
        if (CharSequenceUtil.isBlank(request.getNickname())) {
            member.setNickname(request.getUsername());
        }
        member.setAvatar(StrUtil.isBlank(request.getAvatar()) ? CharSequenceUtil.EMPTY : request.getAvatar());
        member.setFirstInviterId(DsConstant.NULL_MEMBER_INVITER_ID);
        member.setSecondInviterId(DsConstant.NULL_MEMBER_INVITER_ID);
        member.setThirdInviterId(DsConstant.NULL_MEMBER_INVITER_ID);
        if (isJoinCreate) {
            this.setInviterId(member, inviteMember);
        }
        member.setIdentityType(MemberIdentityTypeEnum.GENERAL);
        member.setRankType(MemberRankTypeEnum.BRONZE);
        member.setState(StateEnum.ENABLE);
        Platform currentPlatform = CurrentPlatformHelper.self();
        member.setPlatformUsername(currentPlatform.getUsername());
        member.setPlatformNickname(currentPlatform.getNickname());
        crudMemberService.insert(member);
        Long memberId = member.getId();

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
     * 设置邀请人标识
     * [被邀请人]的一级是[邀请人]，二级是[邀请人]的一级，三级是[邀请人]的二级
     */
    private void setInviterId(Member member, Member inviteMember) {
        member.setFirstInviterId(inviteMember.getId());
        Long secondInviterId = inviteMember.getFirstInviterId();
        if (!DsConstant.NULL_MEMBER_INVITER_ID.equals(secondInviterId)) {
            member.setSecondInviterId(secondInviterId);
        }
        Long thirdInviterId = inviteMember.getSecondInviterId();
        if (!DsConstant.NULL_MEMBER_INVITER_ID.equals(thirdInviterId)) {
            member.setThirdInviterId(thirdInviterId);
        }
    }


}
