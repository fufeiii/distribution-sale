package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.AccountRecordQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.AccountRecordResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudAccountRecordService;
import cn.fufeii.ds.repository.entity.AccountRecord;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 账户变动 Service
 *
 * @author FuFei
 */
@Service
public class AccountRecordService {

    @Autowired
    private CrudAccountRecordService crudAccountRecordService;

    /**
     * 分页查询
     */
    public IPage<AccountRecordResponse> page(AccountRecordQueryRequest pageParam, IPage<AccountRecord> pageable) {
        Wrapper<AccountRecord> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, AccountRecord.class));
        IPage<AccountRecord> selectPage = crudAccountRecordService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            AccountRecordResponse response = new AccountRecordResponse();
            response.setId(it.getId());
            response.setMemberId(it.getMemberId());
            response.setAccountId(it.getAccountId());
            response.setAccountType(it.getAccountType().getMessage());
            response.setBeforeChangeTotal(it.getBeforeChangeTotal());
            response.setAfterChangeTotal(it.getAfterChangeTotal());
            response.setChangeAmount(it.getChangeAmount());
            response.setChangeType(it.getChangeType().getMessage());
            response.setChangeRecordId(it.getChangeRecordId());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

}