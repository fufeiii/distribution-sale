package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.AccountRecordQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.AccountRecordResponse;
import cn.fufeii.ds.admin.service.AccountRecordService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.AccountRecord;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 账户变动 Controller
 *
 * @author FuFei
 */
@Api("账户变动")
@Controller
@RequestMapping("/admin/account-record")
public class AccountRecordController {

    @Autowired
    private AccountRecordService accountRecordService;

    @GetMapping("/")
    public String index() {
        return "/accountRecord/accountRecord.html";
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<AccountRecordResponse> page(@RequestBody AccountRecordQueryRequest pageParam) {
        IPage<AccountRecordResponse> pageResult = accountRecordService.page(pageParam, new Page<AccountRecord>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}