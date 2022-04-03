package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.ProfitParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.ProfitParamUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitParamResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudProfitParamService;
import cn.fufeii.ds.repository.entity.ProfitParam;
import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润参数 Service
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
        LambdaQueryWrapper<ProfitParam> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, ProfitParam.class));
        CurrentUserHelper.setPlatformIfPossible(queryWrapper);
        IPage<ProfitParam> selectPage = crudProfitParamService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            ProfitParamResponse response = new ProfitParamResponse();
            response.setId(it.getId());
            response.setPlatformUsername(it.getPlatformUsername());
            response.setPlatformNickname(it.getPlatformNickname());
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
        // 数据权限
        CurrentUserHelper.checkPlatformThrow(profitParam.getPlatformUsername());
        // 响应
        ProfitParamResponse response = new ProfitParamResponse();
        response.setId(profitParam.getId());
        response.setPlatformUsername(profitParam.getPlatformUsername());
        response.setPlatformNickname(profitParam.getPlatformNickname());
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
    public void create(ProfitParamUpsertRequest request) {
        // 保存分润参数
        ProfitParam profitParam = new ProfitParam();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(request, profitParam);
        SystemUser currentUser = CurrentUserHelper.self();
        profitParam.setPlatformUsername(currentUser.getPlatformUsername());
        profitParam.setPlatformNickname(currentUser.getPlatformNickname());
        crudProfitParamService.insertOrUpdate(profitParam);
    }

    /**
     * 更新
     */
    public void modify(ProfitParamUpsertRequest request) {
        ProfitParam profitParam = crudProfitParamService.selectById(request.getId());
        // 数据权限
        CurrentUserHelper.checkPlatformThrow(profitParam.getPlatformUsername());
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(request, profitParam);
        crudProfitParamService.insertOrUpdate(profitParam);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        ProfitParam profitParam = crudProfitParamService.selectById(id);
        // 数据权限
        CurrentUserHelper.checkPlatformThrow(profitParam.getPlatformUsername());
        crudProfitParamService.deleteById(id);
    }

}