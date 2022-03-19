package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.AccountQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.AccountResponse;
import cn.fufeii.ds.admin.service.AccountService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.Account;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 会员账户 Controller
 *
 * @author FuFei
 */
@Api("账户管理")
@Controller
@RequestMapping("/admin/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/")
    public String index() {
        return "/account/account.html";
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<AccountResponse> page(@RequestBody AccountQueryRequest pageParam) {
        IPage<AccountResponse> pageResult = accountService.page(pageParam, new Page<Account>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}