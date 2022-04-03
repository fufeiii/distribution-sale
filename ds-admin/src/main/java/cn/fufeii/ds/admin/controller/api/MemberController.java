package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.MemberQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.MemberAccountResponse;
import cn.fufeii.ds.admin.model.vo.response.MemberResponse;
import cn.fufeii.ds.admin.service.MemberService;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.Member;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 会员信息 API Controller
 *
 * @author FuFei
 */
@Api(tags = "会员管理")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<MemberResponse> page(@RequestBody MemberQueryRequest pageParam) {
        IPage<MemberResponse> pageResult = memberService.page(pageParam, new Page<Member>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("账户查询")
    @GetMapping("/account/{memberId}")
    public CommonResult<MemberAccountResponse> account(@PathVariable Long memberId) {
        return CommonResult.success(memberService.account(memberId));
    }

    @ApiOperation("启用")
    @PutMapping("/enable/{id}")
    public CommonResult<Void> enable(@PathVariable Long id) {
        memberService.changeState(id, StateEnum.ENABLE);
        return CommonResult.success();
    }

    @ApiOperation("禁用")
    @PutMapping("/disable/{id}")
    public CommonResult<Void> disable(@PathVariable Long id) {
        memberService.changeState(id, StateEnum.DISABLE);
        return CommonResult.success();
    }

}