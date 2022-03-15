package cn.fufeii.ds.admin.controller;

import cn.fufeii.ds.admin.model.vo.request.RankParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.RankParamUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.RankParamResponse;
import cn.fufeii.ds.admin.service.RankParamService;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.RankParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 段位参数 Controller
 *
 * @author FuFei
 */
@Api("段位参数")
@Controller
@RequestMapping("/admin/rank-param")
public class RankParamController {

    @Autowired
    private RankParamService rankParamService;

    /**
     * 封装固定的参数
     */
    private void injectionParam(Model model) {
        // 遍历 用户段位 枚举
        model.addAttribute("mre", MemberRankTypeEnum.getKeyValuePairList());
        // 遍历 状态 枚举
        model.addAttribute("se", StateEnum.getKeyValuePairList());
    }


    @GetMapping("/")
    public String index(Model model) {
        this.injectionParam(model);
        return "/rankParam/rankParam.html";
    }

    /**
     * 新增页面
     */
    @GetMapping("/add")
    public String addPage(Model model) {
        this.injectionParam(model);
        return "/rankParam/rankParamAdd.html";
    }

    /**
     * 编辑页面
     */
    @GetMapping("/edit")
    public String edit(Model model) {
        this.injectionParam(model);
        return "/rankParam/rankParamEdit.html";
    }

    /**
     * 分页查询
     */
    @PostMapping("/page")
    @ResponseBody
    public PageResult<RankParamResponse> page(@RequestBody RankParamQueryRequest pageParam) {
        IPage<RankParamResponse> pageResult = rankParamService.page(pageParam, new Page<RankParam>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    /**
     * 查询详情
     */
    @GetMapping("/get/{id}")
    @ResponseBody
    public CommonResult<RankParamResponse> get(@PathVariable Long id) {
        return CommonResult.success(rankParamService.get(id));
    }

    /**
     * 新增
     */
    @PostMapping("/add")
    @ResponseBody
    public CommonResult<Void> add(@RequestBody RankParamUpsertRequest addParam) {
        rankParamService.add(addParam);
        return CommonResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("/modify")
    @ResponseBody
    public CommonResult<Void> modify(@RequestBody RankParamUpsertRequest modifyParam) {
        rankParamService.modify(modifyParam);
        return CommonResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove/{id}")
    @ResponseBody
    public CommonResult<Void> remove(@PathVariable Long id) {
        rankParamService.remove(id);
        return CommonResult.success();
    }
}