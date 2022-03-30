package cn.fufeii.ds.admin.controller.api;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.ProfitParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.ProfitParamUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitParamResponse;
import cn.fufeii.ds.admin.service.ProfitParamService;
import cn.fufeii.ds.common.result.CommonResult;
import cn.fufeii.ds.common.result.PageResult;
import cn.fufeii.ds.repository.entity.ProfitParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 分润参数 API Controller
 *
 * @author FuFei
 */
@Api(tags = "分润参数")
@RestController
@RequestMapping(DsAdminConstant.API_PATH_PREFIX + "/profit-param")
public class ProfitParamController {

    @Autowired
    private ProfitParamService profitParamService;

    @ApiOperation("分页查询")
    @PostMapping("/page")
    public PageResult<ProfitParamResponse> page(@RequestBody ProfitParamQueryRequest pageParam) {
        IPage<ProfitParamResponse> pageResult = profitParamService.page(pageParam, new Page<ProfitParam>(pageParam.getPage(), pageParam.getSize()).addOrder(OrderItem.desc("id")));
        return PageResult.success(pageResult.getTotal(), pageResult.getRecords());
    }

    @ApiOperation("详情查询")
    @GetMapping("/get/{id}")
    public CommonResult<ProfitParamResponse> get(@PathVariable Long id) {
        return CommonResult.success(profitParamService.get(id));
    }

    @ApiOperation("新增")
    @PostMapping("/create")
    public CommonResult<Void> create(@RequestBody ProfitParamUpsertRequest createParam) {
        profitParamService.create(createParam);
        return CommonResult.success();
    }

    @ApiOperation("修改")
    @PutMapping("/modify")
    public CommonResult<Void> modify(@RequestBody ProfitParamUpsertRequest modifyParam) {
        profitParamService.modify(modifyParam);
        return CommonResult.success();
    }

    @ApiOperation("删除")
    @DeleteMapping("/remove/{id}")
    public CommonResult<Void> remove(@PathVariable Long id) {
        profitParamService.remove(id);
        return CommonResult.success();
    }
}