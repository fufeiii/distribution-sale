package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.ProfitRecordQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitRecordResponse;
import cn.fufeii.ds.admin.service.ProfitRecordService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.ProfitRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 分销记录 Controller
 *
 * @author FuFei
 */
@Api("分销记录")
@Controller
@RequestMapping("/admin/profit-record")
public class ProfitRecordController {

    @Autowired
    private ProfitRecordService profitRecordService;

    @GetMapping("/")
    public String index() {
        return "/profitRecord/profitRecord.html";
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<ProfitRecordResponse> page(@RequestBody ProfitRecordQueryRequest pageParam) {
        IPage<ProfitRecordResponse> pageResult = profitRecordService.page(pageParam, new Page<ProfitRecord>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}