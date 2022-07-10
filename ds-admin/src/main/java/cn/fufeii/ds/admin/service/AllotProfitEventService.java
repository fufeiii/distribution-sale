package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.AllotProfitEventQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.AllotProfitEventResponse;
import cn.fufeii.ds.admin.model.vo.response.ProfitIncomeRecordResponse;
import cn.fufeii.ds.common.enumerate.biz.AccountTypeEnum;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.common.util.DsUtil;
import cn.fufeii.ds.common.util.PageUtil;
import cn.fufeii.ds.repository.crud.CrudAllotProfitEventService;
import cn.fufeii.ds.repository.crud.CrudMemberService;
import cn.fufeii.ds.repository.crud.CrudProfitIncomeRecordService;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import cn.fufeii.ds.repository.entity.BaseEntity;
import cn.fufeii.ds.repository.entity.Member;
import cn.fufeii.ds.repository.entity.ProfitIncomeRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 分润事件 Service
 *
 * @author FuFei
 */
@Service
public class AllotProfitEventService {
    @Autowired
    private CrudAllotProfitEventService crudAllotProfitEventService;
    @Autowired
    private CrudMemberService crudMemberService;
    @Autowired
    private CrudProfitIncomeRecordService crudProfitIncomeRecordService;

    /**
     * 分页查询
     */
    public IPage<AllotProfitEventResponse> page(AllotProfitEventQueryRequest pageParam, IPage<AllotProfitEvent> pageable) {
        Wrapper<AllotProfitEvent> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, AllotProfitEvent.class));
        IPage<AllotProfitEvent> selectPage = crudAllotProfitEventService.selectPage(queryWrapper, pageable);
        if (selectPage.getRecords().isEmpty()) {
            return PageUtil.page(pageParam.getPage(), (int) pageable.getSize());
        }

        List<Long> memberIdList = selectPage.getRecords().stream().map(AllotProfitEvent::getTriggerMemberId).distinct().collect(Collectors.toList());
        Map<Long, Member> memberIdAndMemberMap = crudMemberService.selectList(Wrappers.<Member>lambdaQuery().in(BaseEntity::getId, memberIdList))
                .stream().collect(Collectors.toMap(BaseEntity::getId, Function.identity()));

        // 组装response对象返回
        return selectPage.convert(it -> {
            AllotProfitEventResponse response = new AllotProfitEventResponse();
            response.setId(it.getId());
            response.setProfitType(it.getProfitType().getMessage());
            Member member = memberIdAndMemberMap.get(it.getTriggerMemberId());
            if (member != null) {
                response.setTriggerMember(member.getUsername());
            }
            response.setEventNumber(it.getEventNumber());
            response.setEventAmount(it.getEventAmount());
            response.setMemo(it.getMemo());
            response.setNotifyState(it.getNotifyState().getMessage());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

    /**
     * 详情查询
     */
    public AllotProfitEventResponse info(Long id) {
        AllotProfitEvent allotProfitEvent = crudAllotProfitEventService.selectById(id);
        Member member = crudMemberService.selectById(allotProfitEvent.getTriggerMemberId());
        AllotProfitEventResponse response = new AllotProfitEventResponse();
        response.setId(allotProfitEvent.getId());
        response.setProfitType(allotProfitEvent.getProfitType().getMessage());
        response.setTriggerMember(member.getUsername());
        response.setEventNumber(allotProfitEvent.getEventNumber());
        response.setEventAmount(allotProfitEvent.getEventAmount());
        response.setNotifyState(allotProfitEvent.getNotifyState().getMessage());
        response.setMemo(allotProfitEvent.getMemo());
        response.setCreateDateTime(allotProfitEvent.getCreateDateTime());
        return response;
    }


    /**
     * 查询分润收入记录列表
     *
     * @param id *
     * @return *
     */
    public List<ProfitIncomeRecordResponse> profitIncomeRecordList(Long id) {
        List<ProfitIncomeRecord> profitIncomeRecordList = crudProfitIncomeRecordService.selectList(Wrappers.<ProfitIncomeRecord>lambdaQuery().eq(ProfitIncomeRecord::getProfitEventId, id));
        if (profitIncomeRecordList.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProfitIncomeRecordResponse> responseList = new ArrayList<>(profitIncomeRecordList.size());
        List<Long> impactMemberIdList = profitIncomeRecordList.stream().map(ProfitIncomeRecord::getImpactMemberId).distinct().collect(Collectors.toList());
        Map<Long, Member> memberIdAndMemberMap = crudMemberService.selectList(Wrappers.<Member>lambdaQuery().in(BaseEntity::getId, impactMemberIdList))
                .stream().collect(Collectors.toMap(BaseEntity::getId, Function.identity()));
        for (ProfitIncomeRecord record : profitIncomeRecordList) {
            ProfitIncomeRecordResponse response = new ProfitIncomeRecordResponse();
            response.setId(record.getId());
            response.setProfitEventId(record.getProfitEventId());
            response.setAccountType(record.getAccountType().getMessage());
            Member member = memberIdAndMemberMap.get(record.getImpactMemberId());
            if (member != null) {
                response.setImpactMember(member.getUsername());
            }
            response.setIncomeCount(record.getIncomeCount().toString());
            if (AccountTypeEnum.MONEY == record.getAccountType()) {
                // 单位换算
                response.setIncomeCount(DsUtil.fenToYuan(record.getIncomeCount()));
            }
            response.setMemo(record.getMemo());
            response.setCreateDateTime(record.getCreateDateTime());
            responseList.add(response);
        }
        responseList.addAll(responseList);
        return responseList;
    }
}