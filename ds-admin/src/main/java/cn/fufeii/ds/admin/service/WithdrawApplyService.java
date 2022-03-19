package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.WithdrawApplyQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.WithdrawApplyResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudWithdrawApplyService;
import cn.fufeii.ds.repository.entity.WithdrawApply;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 提现申请 Service
 *
 * @author FuFei
 */
@Service
public class WithdrawApplyService {

    @Autowired
    private CrudWithdrawApplyService crudWithdrawApplyService;

    /**
     * 分页查询
     */
    public IPage<WithdrawApplyResponse> page(WithdrawApplyQueryRequest pageParam, IPage<WithdrawApply> pageable) {
        Wrapper<WithdrawApply> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, WithdrawApply.class));
        IPage<WithdrawApply> selectPage = crudWithdrawApplyService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            WithdrawApplyResponse response = new WithdrawApplyResponse();
            response.setId(it.getId());
            response.setMemberId(it.getMemberId());
            response.setWithdrawNumber(it.getWithdrawNumber());
            response.setWithdrawAmount(it.getWithdrawAmount());
            response.setFeeAmount(it.getFeeAmount());
            response.setWithdrawState(it.getWithdrawState().getMessage());
            response.setWithdrawDesc(it.getWithdrawDesc());
            response.setApprovalTime(it.getApprovalTime());
            response.setApprovalDesc(it.getApprovalDesc());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

}