package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.service.MemberService;
import cn.fufeii.ds.admin.vo.MemberVO;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.Member;
import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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


    @GetMapping
    public String index() {
        return "/member/member.html";
    }


    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<MemberVO> page(@RequestBody MemberVO pageVO) {
        // 构造查询条件、分页条件和排序条件
        Wrapper<Member> query = Wrappers.lambdaQuery(BeanUtil.copyProperties(pageVO, Member.class));
        Page<Member> pageParam = new Page<Member>(pageVO.getPage(), pageVO.getSize()).addOrder(OrderItem.desc("id"));
        // 执行查询
        Page<Member> pageResult = memberService.getCrudMemberService().page(pageParam, query);
        // 拷贝结果
        List<MemberVO> list = pageResult.getRecords().stream().map(it -> BeanUtil.copyProperties(it, MemberVO.class)).collect(Collectors.toList());
        return PageResult.of(pageResult.getTotal(), list);
    }

    /**
     * 查询详情
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public CommonResult<MemberVO> get(@PathVariable Long id) {
        Member entity = memberService.getCrudMemberService().getById(id);
        return CommonResult.success(BeanUtil.copyProperties(entity, MemberVO.class));
    }

}