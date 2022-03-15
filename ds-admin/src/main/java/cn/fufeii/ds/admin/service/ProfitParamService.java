package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.ProfitParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.ProfitParamUpsertRequest;
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
    public IPage<ProfitParamResponse> page(ProfitParamQueryRequest pageParam, IPage<ProfitParam> pageable) {
        Wrapper<ProfitParam> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, ProfitParam.class));
        IPage<ProfitParam> selectPage = crudProfitParamService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            ProfitParamResponse response = new ProfitParamResponse();
            response.setId(it.getId());
            response.setAccountType(it.getAccountType().getMessage());
            response.setProfitType(it.getProfitType().getMessage());
            response.setCalculateMode(it.getCalculateMode().getMessage());
            response.setProfitLevel(it.getProfitLevel().getMessage());
            response.setProfitRatio(it.getProfitRatio());
            response.setMemberIdentityType(it.getMemberIdentityType().getMessage());
            response.setMemberRankType(it.getMemberRankType().getMessage());
            response.setState(it.getState().getMessage());
            response.setCreateDateTime(it.getCreateDateTime());
            response.setUpdateDateTime(it.getUpdateDateTime());
            return response;
        });
    }

    /**
     * 获取
     */
    public ProfitParamResponse get(Long id) {
        ProfitParam profitParam = crudProfitParamService.selectById(id);
        ProfitParamResponse response = new ProfitParamResponse();
        response.setId(profitParam.getId());
        response.setAccountType(profitParam.getAccountType().name());
        response.setProfitType(profitParam.getProfitType().name());
        response.setCalculateMode(profitParam.getCalculateMode().name());
        response.setProfitLevel(profitParam.getProfitLevel().name());
        response.setProfitRatio(profitParam.getProfitRatio());
        response.setMemberIdentityType(profitParam.getMemberIdentityType().name());
        response.setMemberRankType(profitParam.getMemberRankType().name());
        response.setState(profitParam.getState().name());
        response.setCreateDateTime(profitParam.getCreateDateTime());
        response.setUpdateDateTime(profitParam.getUpdateDateTime());
        return response;
    }

    /**
     * 保存
     */
    public void add(ProfitParamUpsertRequest addParam) {
        ProfitParam profitParam = new ProfitParam();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(addParam, profitParam);
        crudProfitParamService.insertOrUpdate(profitParam);
    }

    /**
     * 更新
     */
    public void modify(ProfitParamUpsertRequest modifyParam) {
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