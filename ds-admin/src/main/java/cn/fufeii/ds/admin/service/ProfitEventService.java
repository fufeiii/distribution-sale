package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.ProfitEventQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitEventResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudProfitEventService;
import cn.fufeii.ds.repository.entity.ProfitEvent;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分销事件 Service
 *
 * @author FuFei
 */
@Service
public class ProfitEventService {

    @Autowired
    private CrudProfitEventService crudProfitEventService;

    /**
     * 分页查询
     */
    public IPage<ProfitEventResponse> page(ProfitEventQueryRequest pageParam, IPage<ProfitEvent> pageable) {
        Wrapper<ProfitEvent> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, ProfitEvent.class));
        IPage<ProfitEvent> selectPage = crudProfitEventService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            ProfitEventResponse response = new ProfitEventResponse();
            response.setId(it.getId());
            response.setProfitType(it.getProfitType().getMessage());
            response.setTriggerMemberId(it.getTriggerMemberId());
            response.setEventNumber(it.getEventNumber());
            response.setEventAmount(it.getEventAmount());
            response.setMemo(it.getMemo());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

}