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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
    private void packageModel(Model model) {
        // 遍历 账户类型 枚举
        List<Map<String, Object>> ateList = new LinkedList<>();
        for (AccountTypeEnum accountTypeEnum : AccountTypeEnum.values()) {
            Map<String, Object> ate = new HashMap<>(4);
            ate.put("name", accountTypeEnum.name());
            ate.put("message", accountTypeEnum.getMessage());
            ateList.add(ate);
        }
        model.addAttribute("ate", ateList);

        // 遍历 分润类型 枚举
        List<Map<String, Object>> pteList = new LinkedList<>();
        for (ProfitTypeEnum profitTypeEnum : ProfitTypeEnum.values()) {
            Map<String, Object> pte = new HashMap<>(4);
            pte.put("name", profitTypeEnum.name());
            pte.put("message", profitTypeEnum.getMessage());
            pteList.add(pte);
        }
        model.addAttribute("pte", pteList);

        // 遍历 分润计算方式 枚举
        List<Map<String, Object>> cmeList = new LinkedList<>();
        for (CalculateModeEnum calculateModeEnum : CalculateModeEnum.values()) {
            Map<String, Object> cme = new HashMap<>(4);
            cme.put("name", calculateModeEnum.name());
            cme.put("message", calculateModeEnum.getMessage());
            cmeList.add(cme);
        }
        model.addAttribute("cme", cmeList);

        // 分销等级 暂时先这样模拟一下
        List<Map<String, Object>> levelList = new LinkedList<>();
        for (ProfitLevelEnum profitLevelEnum : ProfitLevelEnum.values()) {
            Map<String, Object> le = new HashMap<>(4);
            le.put("name", profitLevelEnum.name());
            le.put("message", profitLevelEnum.getMessage());
            levelList.add(le);
        }
        model.addAttribute("ple", levelList);

        // 遍历 用户段位 枚举
        List<Map<String, Object>> mreList = new LinkedList<>();
        for (MemberRankTypeEnum memberRankTypeEnum : MemberRankTypeEnum.values()) {
            Map<String, Object> mre = new HashMap<>(4);
            mre.put("name", memberRankTypeEnum.name());
            mre.put("message", memberRankTypeEnum.getMessage());
            mreList.add(mre);
        }
        model.addAttribute("mre", mreList);

        // 遍历 用户身份 枚举
        List<Map<String, Object>> mteList = new LinkedList<>();
        for (MemberIdentityTypeEnum memberIdentityTypeEnum : MemberIdentityTypeEnum.values()) {
            Map<String, Object> mte = new HashMap<>(4);
            mte.put("name", memberIdentityTypeEnum.name());
            mte.put("message", memberIdentityTypeEnum.getMessage());
            mteList.add(mte);
        }
        model.addAttribute("mte", mteList);

        // 遍历 状态 枚举
        List<Map<String, Object>> seList = new LinkedList<>();
        for (StateEnum stateEnum : StateEnum.values()) {
            Map<String, Object> se = new HashMap<>(4);
            se.put("name", stateEnum.name());
            se.put("message", stateEnum.getMessage());
            seList.add(se);
        }
        model.addAttribute("se", seList);
    }


    @GetMapping("/")
    public String index(Model model) {
        this.packageModel(model);
        return "/profitParam/profitParam.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String addPage(Model model) {
        this.packageModel(model);
        return "/profitParam/profitParamAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String edit(Model model) {
        this.packageModel(model);
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
    public CommonResult<Void> add(@RequestBody Object addParam) {
//        profitParamService.add(addParam);
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