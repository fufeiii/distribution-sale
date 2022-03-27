package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.MemberQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.MemberResponse;
import cn.fufeii.ds.admin.service.MemberService;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.Member;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员信息 API Controller
 *
 * @author FuFei
 */
@Api("会员管理")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    /**
     * 分页查询
     */
    @PostMapping("/page")
    public PageResult<MemberResponse> page(@RequestBody MemberQueryRequest pageParam) {
        IPage<MemberResponse> pageResult = memberService.page(pageParam, new Page<Member>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

}