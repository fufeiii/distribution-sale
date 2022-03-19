package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.ProfitEventQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitEventResponse;
import cn.fufeii.ds.admin.service.ProfitEventService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.ProfitEvent;
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
@Api("分销事件")
@Controller
@RequestMapping("/admin/profit-event")
public class ProfitEventController {

    @Autowired
    private ProfitEventService profitEventService;

    @GetMapping("/")
    public String index() {
        return "/profitEvent/profitEvent.html";
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<ProfitEventResponse> page(@RequestBody ProfitEventQueryRequest pageParam) {
        IPage<ProfitEventResponse> pageResult = profitEventService.page(pageParam, new Page<ProfitEvent>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}