package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.ProfitParamRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitParamResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudProfitParamService;
import cn.fufeii.ds.repository.entity.ProfitParam;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润参数 Service Impl
 * 业务实现
 *
 * @author FuFei
 */
@Service
public class ProfitParamService {

    @Autowired
    private CrudProfitParamService crudProfitParamService;

    /**
     * 分页查询
     */
    public IPage<ProfitParamResponse> page(ProfitParamRequest pageParam, IPage<ProfitParam> pageable) {
        Wrapper<ProfitParam> queryWrapper = Wrappers.lambdaQuery();
        IPage<ProfitParam> selectPage = crudProfitParamService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        IPage<ProfitParamResponse> respPage = selectPage.convert(it -> {
            ProfitParamResponse profitParamResponse = new ProfitParamResponse();
            // 建议使用setter，字段类型问题能在编译期发现
            BeanCopierUtil.copy(it, profitParamResponse);
            return profitParamResponse;
        });
        return respPage;
    }

    /**
     * 获取
     */
    public ProfitParamResponse get(Long id) {
        ProfitParam profitParam = crudProfitParamService.selectById(id);
        ProfitParamResponse profitParamResp = new ProfitParamResponse();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(profitParam, profitParamResp);
        return profitParamResp;
    }

    /**
     * 保存
     */
    public void add(ProfitParamRequest addParam) {
        ProfitParam profitParam = new ProfitParam();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(addParam, profitParam);
        crudProfitParamService.insertOrUpdate(profitParam);
    }

    /**
     * 更新
     */
    public void modify(ProfitParamRequest modifyParam) {
        ProfitParam profitParam = new ProfitParam();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(modifyParam, profitParam);
        crudProfitParamService.insertOrUpdate(profitParam);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        crudProfitParamService.deleteById(id);
    }

}