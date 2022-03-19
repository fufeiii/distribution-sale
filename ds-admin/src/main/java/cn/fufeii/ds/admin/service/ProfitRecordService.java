package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.ProfitRecordQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitRecordResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudProfitRecordService;
import cn.fufeii.ds.repository.entity.ProfitRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分销记录 Service
 *
 * @author FuFei
 */
@Service
public class ProfitRecordService {

    @Autowired
    private CrudProfitRecordService crudProfitRecordService;

    /**
     * 分页查询
     */
    public IPage<ProfitRecordResponse> page(ProfitRecordQueryRequest pageParam, IPage<ProfitRecord> pageable) {
        Wrapper<ProfitRecord> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, ProfitRecord.class));
        IPage<ProfitRecord> selectPage = crudProfitRecordService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            ProfitRecordResponse response = new ProfitRecordResponse();
            response.setId(it.getId());
            response.setProfitEventId(it.getProfitEventId());
            response.setAccountType(it.getAccountType().getMessage());
            response.setImpactMemberId(it.getImpactMemberId());
            response.setIncomeCount(it.getIncomeCount());
            response.setMemo(it.getMemo());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

}