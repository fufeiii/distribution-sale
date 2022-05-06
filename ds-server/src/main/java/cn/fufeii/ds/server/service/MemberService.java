package cn.fufeii.ds.server.service;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.constant.DsConstant;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.*;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.crud.CrudAccountChangeRecordService;
import cn.fufeii.ds.repository.crud.CrudAccountService;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.crud.CrudProfitIncomeRecordService;
import cn.fufeii.ds.repository.entity.Account;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.Platform;
import cn.fufeii.ds.repository.entity.ProfitIncomeRecord;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.model.api.request.MemberCreateRequest;
import cn.fufeii.ds.server.model.api.request.MemberIdentityTypeRequest;
import cn.fufeii.ds.server.model.api.response.*;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.InviteEvent;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    @Autowired
    private CrudProfitIncomeRecordService crudProfitIncomeRecordService;
    @Autowired
    private CrudAccountChangeRecordService crudAccountChangeRecordService;

    @GlobalLock(key = DsServerConstant.CURRENT_PLATFORM_USERNAME_SPEL + "#request.username")
    @Transactional
    public MemberCreateResponse create(MemberCreateRequest request) {
        Platform currentPlatform = CurrentPlatformHelper.self();
        String platformUsername = currentPlatform.getUsername();
        // 检查用户是否存在
        Optional<Member> memberOptional = crudMemberService.selectByUsernameAndPlatformUsernameOptional(request.getUsername(), platformUsername);
        if (memberOptional.isPresent()) {
            throw BizException.serverError(String.format("会员[%s]已存在", request.getUsername()));
        }
        // 如果被邀请加入的, 则检查邀请人
        boolean isJoinCreate = CharSequenceUtil.isNotBlank(request.getInviteUsername());
        Member inviterMember = null;
        if (isJoinCreate) {
            Optional<Member> inviteMemberOpt = crudMemberService.selectByUsernameAndPlatformUsernameOptional(request.getInviteUsername(), platformUsername);
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
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(username, platformUsername);
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
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(request.getUsername(), CurrentPlatformHelper.username());
        // 有必要告诉上有系统, 提交重复了或者不正确的更新
        if (request.getIdentityType() == member.getIdentityType()) {
            throw new BizException(ExceptionEnum.BIZ_COMMON_ERROR, "重复更新会员身份");
        }
        member.setIdentityType(request.getIdentityType());
        crudMemberService.updateById(member);
    }


    /**
     * 查询会员详情
     *
     * @param username       *
     * @param includeAccount *
     */
    public MemberResponse info(String username, Boolean includeAccount) {
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(username, CurrentPlatformHelper.username());
        MemberResponse response = new MemberResponse();
        response.setId(member.getId());
        response.setPlatformUsername(member.getPlatformUsername());
        response.setPlatformNickname(member.getPlatformNickname());
        response.setUsername(member.getUsername());
        response.setNickname(member.getNickname());
        response.setAvatar(member.getAvatar());

        // 查询邀请人
        Set<Long> inviterIdSet = Stream.of(member.getFirstInviterId(), member.getSecondInviterId(), member.getThirdInviterId())
                .filter(it -> !DsConstant.NULL_MEMBER_INVITER_ID.equals(it)).collect(Collectors.toSet());
        Map<Long, Member> inviterIdMap = Collections.emptyMap();
        if (!inviterIdSet.isEmpty()) {
            inviterIdMap = crudMemberService.selectList(Wrappers.<Member>lambdaQuery().in(Member::getId, inviterIdSet))
                    .stream().collect(Collectors.toMap(Member::getId, Function.identity()));
        }
        Optional<Member> firstInviterOptional = Optional.ofNullable(inviterIdMap.get(member.getFirstInviterId()));
        if (firstInviterOptional.isPresent()) {
            Member inviter = firstInviterOptional.get();
            response.setFirstInviterId(inviter.getId());
            response.setFirstInviterUsername(inviter.getUsername());
        }
        Optional<Member> secondInviterOptional = Optional.ofNullable(inviterIdMap.get(member.getSecondInviterId()));
        if (secondInviterOptional.isPresent()) {
            Member inviter = secondInviterOptional.get();
            response.setSecondInviterId(inviter.getId());
            response.setSecondInviterUsername(inviter.getUsername());
        }
        Optional<Member> thirdInviterOptional = Optional.ofNullable(inviterIdMap.get(member.getThirdInviterId()));
        if (thirdInviterOptional.isPresent()) {
            Member inviter = thirdInviterOptional.get();
            response.setThirdInviterId(inviter.getId());
            response.setThirdInviterUsername(inviter.getUsername());
        }
        response.setIdentityType(member.getIdentityType());
        response.setRankType(member.getRankType());
        response.setState(member.getState());

        // 按需返回账户内容
        if (includeAccount) {
            Account account = crudAccountService.selectByMemberId(member.getId());
            response.setMoneyTotalHistory(account.getMoneyTotalHistory());
            response.setMoneyTotal(account.getMoneyTotal());
            response.setMoneyAvailable(account.getMoneyAvailable());
            response.setMoneyFrozen(account.getMoneyFrozen());
            response.setPointsTotalHistory(account.getPointsTotalHistory());
            response.setPointsTotal(account.getPointsTotal());
            response.setPointsAvailable(account.getPointsAvailable());
            response.setPointsFrozen(account.getPointsFrozen());
        }
        return response;
    }

    /**
     * 查询会员下级团队
     *
     * @param level    等级枚举
     * @param username 会员标识
     * @param page     *
     * @param size     *
     */
    public IPage<MemberTeamResponse> teamPage(ProfitLevelEnum level, String username, Integer page, Integer size) {
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(username, CurrentPlatformHelper.username());
        Long inviterId = member.getId();
        LambdaQueryWrapper<Member> lambdaQueryWrapper = Wrappers.lambdaQuery();
        if (ProfitLevelEnum.ONE == level) {
            lambdaQueryWrapper.eq(Member::getFirstInviterId, inviterId);
        }
        if (ProfitLevelEnum.TWO == level) {
            lambdaQueryWrapper.eq(Member::getSecondInviterId, inviterId);
        }
        if (ProfitLevelEnum.THREE == level) {
            lambdaQueryWrapper.eq(Member::getThirdInviterId, inviterId);
        }
        IPage<Member> memberIPage = crudMemberService.selectPage(lambdaQueryWrapper, Page.of(page, size));
        return memberIPage.convert(it -> {
            MemberTeamResponse response = new MemberTeamResponse();
            response.setId(it.getId());
            response.setPlatformUsername(it.getPlatformUsername());
            response.setPlatformNickname(it.getPlatformNickname());
            response.setUsername(it.getUsername());
            response.setNickname(it.getNickname());
            response.setAvatar(it.getAvatar());
            response.setIdentityType(it.getIdentityType());
            response.setRankType(it.getRankType());
            response.setState(it.getState());
            return response;
        });
    }


    /**
     * 查询分润记录
     *
     * @param memberUsername 会员标识
     * @param page           *
     * @param size           *
     */
    public IPage<ProfitIncomeRecordResponse> profitIncomeRecordRecordPage(String memberUsername, Integer page, Integer size) {
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(memberUsername, CurrentPlatformHelper.username());
        LambdaQueryWrapper<ProfitIncomeRecord> lambdaQueryWrapper = Wrappers.<ProfitIncomeRecord>lambdaQuery().eq(ProfitIncomeRecord::getImpactMemberId, member.getId());
        IPage<ProfitIncomeRecord> profitRecordIPage = crudProfitIncomeRecordService.selectPage(lambdaQueryWrapper, Page.of(page, size));
        return profitRecordIPage.convert(it -> {
            ProfitIncomeRecordResponse response = new ProfitIncomeRecordResponse();
            response.setId(it.getId());
            response.setProfitEventId(it.getProfitEventId());
            response.setAccountType(it.getAccountType());
            response.setImpactMemberId(it.getImpactMemberId());
            response.setImpactMemberUsername(memberUsername);
            response.setIncomeCount(it.getIncomeCount());
            response.setMemo(it.getMemo());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }


    /**
     * @param memberUsername 会员标识
     * @param page           *
     * @param size           *
     */
    public IPage<AccountChangeRecordResponse> accountChangeRecordRecordPage(String memberUsername, Integer page, Integer size) {
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(memberUsername, CurrentPlatformHelper.username());
        return crudAccountChangeRecordService.selectByMemberIdPage(member.getId(), Page.of(page, size))
                .convert(it -> {
                    AccountChangeRecordResponse response = new AccountChangeRecordResponse();
                    response.setId(it.getId());
                    response.setMemberId(it.getMemberId());
                    response.setAccountId(it.getAccountId());
                    response.setMemberUsername(memberUsername);
                    response.setAccountType(it.getAccountType().name());
                    response.setBeforeAvailableCount(it.getBeforeAvailableCount());
                    response.setAfterAvailableCount(it.getAfterAvailableCount());
                    response.setChangeCount(it.getChangeCount());
                    response.setChangeType(it.getChangeType().name());
                    response.setChangeBizNumber(it.getChangeBizNumber());
                    response.setMemo(it.getMemo());
                    response.setCreateDateTime(it.getCreateDateTime());
                    return response;
                });
    }
}
