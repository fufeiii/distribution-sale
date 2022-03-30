package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.WithdrawApplyQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.WithdrawApplyResponse;
import cn.fufeii.ds.admin.service.WithdrawApplyService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.WithdrawApply;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 提现申请管理 API Controller
 *
 * @author FuFei
 */
@Api(tags = "提现申请管理")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/withdraw-apply")
public class WithdrawApplyController {

    @Autowired
    private WithdrawApplyService withdrawApplyService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<WithdrawApplyResponse> page(@RequestBody WithdrawApplyQueryRequest pageParam) {
        IPage<WithdrawApplyResponse> pageResult = withdrawApplyService.page(pageParam, new Page<WithdrawApply>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}