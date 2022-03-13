package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.ProfitParamRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitParamResponse;
import cn.fufeii.ds.admin.service.ProfitParamService;
import cn.fufeii.ds.common.enumerate.biz.*;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.ProfitParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 会员信息 Controller
 *
 * @author FuFei
 */
@Controller
@RequestMapping("/admin/profit-param")
public class ProfitParamController {

    @Autowired
    private ProfitParamService profitParamService;

    /**
     * 封装固定的参数
     */
    private void injectionParam(Model model) {
        // 遍历 账户类型 枚举
        model.addAttribute("ate", AccountTypeEnum.getKeyValuePairList());
        // 遍历 分润类型 枚举
        model.addAttribute("pte", ProfitTypeEnum.getKeyValuePairList());
        // 遍历 分润计算方式 枚举
        model.addAttribute("cme", CalculateModeEnum.getKeyValuePairList());
        // 分销等级 暂时先这样模拟一下
        model.addAttribute("ple", ProfitLevelEnum.getKeyValuePairList());
        // 遍历 用户段位 枚举
        model.addAttribute("mre", MemberRankTypeEnum.getKeyValuePairList());
        // 遍历 用户身份 枚举
        model.addAttribute("mte", MemberIdentityTypeEnum.getKeyValuePairList());
        // 遍历 状态 枚举
        model.addAttribute("se", StateEnum.getKeyValuePairList());
    }


    @GetMapping("/")
    public String index(Model model) {
        this.injectionParam(model);
        return "/profitParam/profitParam.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String addPage(Model model) {
        this.injectionParam(model);
        return "/profitParam/profitParamAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String edit(Model model) {
        this.injectionParam(model);
        return "/profitParam/profitParamEdit.html";
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<ProfitParamResponse> page(@RequestBody ProfitParamRequest pageParam) {
        IPage<ProfitParamResponse> pageResult = profitParamService.page(pageParam, new Page<ProfitParam>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    /**
     * 查询详情
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public CommonResult<ProfitParamResponse> get(@PathVariable Long id) {
        return CommonResult.success(profitParamService.get(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Void> add(@RequestBody ProfitParamRequest addParam) {
        profitParamService.add(addParam);
        return CommonResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("/modify")
    @ResponseBody
    public CommonResult<Void> modify(@RequestBody ProfitParamRequest modifyParam) {
        profitParamService.modify(modifyParam);
        return CommonResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public CommonResult<Void> remove(@PathVariable Long id) {
        profitParamService.remove(id);
        return CommonResult.success();
    }
}