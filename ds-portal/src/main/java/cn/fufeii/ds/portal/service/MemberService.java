package cn.fufeii.ds.portal.service;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.enumerate.biz.MemberIdentityTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.portal.model.api.request.MemberCreateRequest;
import cn.fufeii.ds.portal.model.api.response.MemberCreateResponse;
import cn.fufeii.ds.portal.ps.event.InviteEvent;
import cn.fufeii.ds.repository.crud.CrudAccountService;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.entity.Account;
import cn.fufeii.ds.repository.entity.Member;
import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @GlobalLock(key = "#request.username")
    @Transactional(rollbackFor = Exception.class)
    public MemberCreateResponse create(MemberCreateRequest request) {
        // 检查用户是否存在
        if (crudMemberService.existByUsername(request.getUsername())) {
            throw BizException.serverError(String.format("会员[%s]已存在", request.getUsername()));
        }
        // 如果被邀请加入的，则检查邀请人
        boolean isJoinCreate = CharSequenceUtil.isNotBlank(request.getInviteUsername());
        if (isJoinCreate) {
            if (crudMemberService.existByUsername(request.getInviteUsername())) {
                throw BizException.serverError(String.format("邀请人[%s]已存在", request.getInviteUsername()));
            }
        }

        // 创建用户
        Member member = new Member();
        member.setUsername(request.getUsername());
        member.setNickname(request.getNickname());
        member.setAvatar(StrUtil.isBlank(request.getAvatar()) ? CharSequenceUtil.EMPTY : request.getAvatar());
        if (isJoinCreate) {
            // TODO 设置层级
            member.setFirParent(null);
            member.setSecParent(null);
            member.setThrParent(null);
            member.setParentPath(null);
        }
        member.setIdentityType(MemberIdentityTypeEnum.GENERAL);
        member.setRankType(MemberRankTypeEnum.BRONZE);
        member.setState(StateEnum.ENABLE);
        crudMemberService.insertOrUpdate(member);
        Long memberId = member.getId();

        // 创建账户
        Account account = new Account();
        account.setMemberId(memberId);
        account.setMoneyTotal(0);
        account.setMoneyAvailable(0);
        account.setMoneyFrozen(0);
        account.setPointsTotalHistory(0);
        account.setPointsTotal(0);
        account.setPointsAvailable(0);
        account.setPointsFrozen(0);
        crudAccountService.insertOrUpdate(account);

        // 发布邀请事件
        if (isJoinCreate) {
            applicationEventPublisher.publishEvent(new InviteEvent(memberId));
        }

        // 返回
        MemberCreateResponse response = new MemberCreateResponse();
        response.setMemberId(memberId);
        return response;
    }


}
