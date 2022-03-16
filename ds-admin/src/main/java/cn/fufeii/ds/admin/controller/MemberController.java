package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.MemberRequest;
import cn.fufeii.ds.admin.model.vo.response.MemberResponse;
import cn.fufeii.ds.admin.service.MemberService;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.Member;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 会员信息 Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping("/admin/member")
public class MemberController {

    @Autowired
    private MemberService memberService;


    @GetMapping("/")
    public String index() {
        return "/member/member.html";
    }

    @GetMapping("/detail")
    public String detail() {
        return "/member/memberDetail.html";
    }


    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<MemberResponse> page(@RequestBody MemberRequest pageParam) {
        IPage<MemberResponse> pageResult = memberService.page(pageParam, new Page<Member>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    /**
     * 查询详情
     */
    @GetMapping("/get/{id}")
    public CommonResult<MemberResponse> get(@PathVariable Long id) {
        return CommonResult.success(memberService.get(id));
    }

}