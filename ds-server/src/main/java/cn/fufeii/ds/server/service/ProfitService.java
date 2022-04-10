package cn.fufeii.ds.server.service;

import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.server.model.api.request.ProfitTradeRequest;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.TradeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

/**
 * ProfitService
 *
 * @author FuFei
 * @date 2022/4/10
 */
@Service
public class ProfitService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private CrudMemberService crudMemberService;

    /**
     * 金钱交易-分润请求
     */
    public void trade(ProfitTradeRequest request) {
        // 组装事件
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(request.getUsername(), CurrentPlatformHelper.username());
        TradeEvent.Source source = new TradeEvent.Source();
        source.setMemberId(member.getId());
        source.setTradeAmount(request.getTradeAmount());
        source.setTradeNumber(request.getTradeNumber());
        // 发布事件
        applicationEventPublisher.publishEvent(new TradeEvent(ProfitTypeEnum.TRADE, source));
    }

}
