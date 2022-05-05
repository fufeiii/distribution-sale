package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.ProfitIncomeRecordQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitIncomeRecordResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudProfitIncomeRecordService;
import cn.fufeii.ds.repository.entity.ProfitIncomeRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润记录 Service
 *
 * @author FuFei
 */
@Service
public class ProfitRecordService {

    @Autowired
    private CrudProfitIncomeRecordService crudProfitIncomeRecordService;

    /**
     * 分页查询
     */
    public IPage<ProfitIncomeRecordResponse> page(ProfitIncomeRecordQueryRequest pageParam, IPage<ProfitIncomeRecord> pageable) {
        Wrapper<ProfitIncomeRecord> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, ProfitIncomeRecord.class));
        IPage<ProfitIncomeRecord> selectPage = crudProfitIncomeRecordService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            ProfitIncomeRecordResponse response = new ProfitIncomeRecordResponse();
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