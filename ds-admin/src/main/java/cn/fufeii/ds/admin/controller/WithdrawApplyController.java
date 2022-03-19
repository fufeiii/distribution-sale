package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.WithdrawApplyQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.WithdrawApplyResponse;
import cn.fufeii.ds.admin.service.WithdrawApplyService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.WithdrawApply;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 分销事件 Controller
 *
 * @author FuFei
 */
@Api("提现申请")
@Controller
@RequestMapping("/admin/withdraw-apply")
public class WithdrawApplyController {

    @Autowired
    private WithdrawApplyService withdrawApplyService;

    @GetMapping("/")
    public String index() {
        return "/withdrawApply/withdrawApply.html";
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<WithdrawApplyResponse> page(@RequestBody WithdrawApplyQueryRequest pageParam) {
        IPage<WithdrawApplyResponse> pageResult = withdrawApplyService.page(pageParam, new Page<WithdrawApply>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}