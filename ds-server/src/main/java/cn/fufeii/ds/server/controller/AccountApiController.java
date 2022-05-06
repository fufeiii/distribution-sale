package cn.fufeii.ds.server.controller;

import cn.fufeii.ds.common.annotation.DataValid;
import cn.fufeii.ds.common.model.CommonResult;
import cn.fufeii.ds.server.model.api.request.AccountAlterRequest;
import cn.fufeii.ds.server.service.AccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/alter")
    public CommonResult<Void> alter(@RequestBody AccountAlterRequest request) {
        accountService.alter(request);
        return CommonResult.success();
    }

}
