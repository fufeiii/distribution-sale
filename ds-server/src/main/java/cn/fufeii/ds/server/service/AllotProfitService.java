package cn.fufeii.ds.server.service;

import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.ProfitTypeEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.crud.CrudAllotProfitEventService;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.crud.CrudProfitIncomeRecordService;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.ProfitIncomeRecord;
import cn.fufeii.ds.server.config.constant.DsServerConstant;
import cn.fufeii.ds.server.model.api.request.ProfitTradeRequest;
import cn.fufeii.ds.server.model.api.response.AllotProfitEventInfoResponse;
import cn.fufeii.ds.server.model.api.response.ProfitTradeResponse;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import cn.fufeii.ds.server.subscribe.event.TradeEvent;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ProfitService
 *
 * @author FuFei
 * @date 2022/4/10
 */
@Service
public class AllotProfitService {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private CrudMemberService crudMemberService;
    @Autowired
    private CrudProfitIncomeRecordService crudProfitIncomeRecordService;
    @Autowired
    private CrudAllotProfitEventService crudAllotProfitEventService;

    /**
     * 金钱交易-分润请求
     */
    @GlobalLock(key = DsServerConstant.CPUS + "#request.tradeNumber")
    public ProfitTradeResponse trade(ProfitTradeRequest request) {
        // 检查是否已经存在了该事件
        LambdaQueryWrapper<AllotProfitEvent> lambdaQueryWrapper = Wrappers.<AllotProfitEvent>lambdaQuery()
                .eq(AllotProfitEvent::getEventNumber, request.getTradeNumber())
                .eq(AllotProfitEvent::getPlatformUsername, CurrentPlatformHelper.username());
        if (crudAllotProfitEventService.exist(lambdaQueryWrapper)) {
            throw new BizException(ExceptionEnum.PROFIT_EVENT_EXIST, request.getTradeNumber());
        }
        // 组装事件
        Member member = crudMemberService.selectByUsernameAndPlatformUsername(request.getUsername(), CurrentPlatformHelper.username());
        TradeEvent.Source source = new TradeEvent.Source();
        source.setMemberId(member.getId());
        source.setTradeAmount(request.getTradeAmount());
        source.setTradeNumber(request.getTradeNumber());
        // 发布事件
        applicationEventPublisher.publishEvent(new TradeEvent(ProfitTypeEnum.TRADE, source));

        // FIXME 分润成功异步通知
        // 这里是临时代码，任何分润成功后应该异步通知上游系统分润成功了，以便上游系统调用[分润事件查询]接口获取分润事件的详情
        ProfitTradeResponse response = new ProfitTradeResponse();
        AllotProfitEvent profitEvent = crudAllotProfitEventService.selectOne(lambdaQueryWrapper);
        response.setEventId(profitEvent.getId());
        return response;
    }


    /**
     * 查询分润事件
     *
     * @param eventId 事件主键
     */
    public AllotProfitEventInfoResponse event(Long eventId) {
        AllotProfitEvent profitEvent = crudAllotProfitEventService.selectByIdAndPlatformUsername(eventId, CurrentPlatformHelper.username());
        // 通用组装
        AllotProfitEventInfoResponse response = new AllotProfitEventInfoResponse();
        response.setProfitType(profitEvent.getProfitType());
        response.setTriggerMemberId(profitEvent.getTriggerMemberId());
        response.setEventNumber(profitEvent.getEventNumber());
        response.setEventAmount(profitEvent.getEventAmount());
        response.setMemo(profitEvent.getMemo());
        response.setCreateDateTime(profitEvent.getCreateDateTime());
        // 组装分润记录
        List<ProfitIncomeRecord> profitRecordList = crudProfitIncomeRecordService.selectByProfitEventId(eventId);
        List<AllotProfitEventInfoResponse.ProfitRecordInfo> profitRecordInfoList = new ArrayList<>(profitRecordList.size());
        for (ProfitIncomeRecord profitRecord : profitRecordList) {
            AllotProfitEventInfoResponse.ProfitRecordInfo profitRecordInfo = new AllotProfitEventInfoResponse.ProfitRecordInfo();
            profitRecordInfo.setId(profitRecord.getId());
            profitRecordInfo.setProfitEventId(eventId);
            profitRecordInfo.setAccountType(profitRecord.getAccountType());
            profitRecordInfo.setImpactMemberId(profitRecord.getImpactMemberId());
            profitRecordInfo.setIncomeCount(profitRecord.getIncomeCount());
            profitRecordInfo.setMemo(profitRecord.getMemo());
            profitRecordInfo.setCreateDateTime(profitRecord.getCreateDateTime());
            profitRecordInfoList.add(profitRecordInfo);
        }
        response.setProfitRecordInfoList(profitRecordInfoList);
        return response;
    }

}
