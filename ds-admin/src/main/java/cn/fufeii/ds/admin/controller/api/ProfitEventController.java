package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.ProfitEventQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitEventResponse;
import cn.fufeii.ds.admin.service.ProfitEventService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.ProfitEvent;
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
 * 分销事件 API Controller
 *
 * @author FuFei
 */
@Api(tags = "分销事件")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/profit-event")
public class ProfitEventController {

    @Autowired
    private ProfitEventService profitEventService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<ProfitEventResponse> page(@RequestBody ProfitEventQueryRequest pageParam) {
        IPage<ProfitEventResponse> pageResult = profitEventService.page(pageParam, new Page<ProfitEvent>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}