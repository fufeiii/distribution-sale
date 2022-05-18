package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.common.model.PageRequest;
import cn.fufeii.ds.common.model.PageResult;
import cn.fufeii.ds.server.model.api.request.AccountChangeRequest;
import cn.fufeii.ds.server.model.api.response.AccountChangeRecordResponse;
import cn.fufeii.ds.server.service.AccountService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 账户 API controller
 *
 * @author FuFei
 */
@Api(tags = "账户API")
@DataValid
@RestController
@RequestMapping("/api/account")
public class AccountApiController {
    @Autowired
    private AccountService accountService;

    @ApiOperation("账户变动")
    @PostMapping("/change")
    public CommonResult<Void> change(@RequestBody AccountChangeRequest request) {
        accountService.change(request);
        return CommonResult.success();
    }

    @ApiOperation("分页查询会员账户变动记录")
    @GetMapping("/change-record/page")
    public PageResult<AccountChangeRecordResponse> accountChangeRecordRecordPage(@RequestParam String username, PageRequest pageRequest) {
        IPage<AccountChangeRecordResponse> pageResult = accountService.accountChangeRecordRecordPage(username, pageRequest.getPage(), pageRequest.getSize());
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}
