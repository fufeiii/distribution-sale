package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.model.vo.request.RankParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.RankParamUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.RankParamResponse;
import cn.fufeii.ds.admin.service.RankParamService;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.RankParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 段位参数 API Controller
 *
 * @author FuFei
 */
@Api("段位参数")
@RestController
@RequestMapping("/admin/rank-param")
public class RankParamController {

    @Autowired
    private RankParamService rankParamService;

    /**
     * 分页查询
     */
    @PostMapping("/page")
    public PageResult<RankParamResponse> page(@RequestBody RankParamQueryRequest pageParam) {
        IPage<RankParamResponse> pageResult = rankParamService.page(pageParam, new Page<RankParam>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    /**
     * 查询详情
     */
    @GetMapping("/get/{id}")
    public CommonResult<RankParamResponse> get(@PathVariable Long id) {
        return CommonResult.success(rankParamService.get(id));
    }

    /**
     * 新增
     */
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody RankParamUpsertRequest createParam) {
        rankParamService.create(createParam);
        return CommonResult.success();
    }

    /**
     * 修改
     */
    @PutMapping("/modify")
    public CommonResult<Void> modify(@RequestBody RankParamUpsertRequest modifyParam) {
        rankParamService.modify(modifyParam);
        return CommonResult.success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/remove/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        rankParamService.remove(id);
        return CommonResult.success();
    }
}