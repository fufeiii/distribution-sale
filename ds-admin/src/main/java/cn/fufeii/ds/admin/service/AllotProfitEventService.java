package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.AllotProfitEventQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.AllotProfitEventResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudAllotProfitEventService;
import cn.fufeii.ds.repository.entity.AllotProfitEvent;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润事件 Service
 *
 * @author FuFei
 */
@Service
public class AllotProfitEventService {

    @Autowired
    private CrudAllotProfitEventService crudAllotProfitEventService;

    /**
     * 分页查询
     */
    public IPage<AllotProfitEventResponse> page(AllotProfitEventQueryRequest pageParam, IPage<AllotProfitEvent> pageable) {
        Wrapper<AllotProfitEvent> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, AllotProfitEvent.class));
        IPage<AllotProfitEvent> selectPage = crudAllotProfitEventService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            AllotProfitEventResponse response = new AllotProfitEventResponse();
            response.setId(it.getId());
            response.setProfitType(it.getProfitType().getMessage());
            response.setTriggerMemberId(it.getTriggerMemberId());
            response.setEventNumber(it.getEventNumber());
            response.setEventAmount(it.getEventAmount());
            response.setMemo(it.getMemo());
            response.setNotifyState(it.getNotifyState().getMsg());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

}