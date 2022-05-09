package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.model.vo.request.AllotProfitConfigQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.AllotProfitConfigUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.AllotProfitConfigResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.annotation.GlobalLock;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.common.util.LockTemplate;
import cn.fufeii.ds.repository.crud.CrudAllotProfitConfigService;
import cn.fufeii.ds.repository.entity.AllotProfitConfig;
import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润配置 Service
 *
 * @author FuFei
 */
@Slf4j
@Service
public class AllotProfitConfigService {

    @Autowired
    private CrudAllotProfitConfigService crudAllotProfitConfigService;
    @Autowired
    private LockTemplate lockTemplate;

    /**
     * 分页查询
     */
    public IPage<AllotProfitConfigResponse> page(AllotProfitConfigQueryRequest pageParam, IPage<AllotProfitConfig> pageable) {
        LambdaQueryWrapper<AllotProfitConfig> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, AllotProfitConfig.class));
        CurrentUserHelper.setPlatformIfPossible(queryWrapper);
        IPage<AllotProfitConfig> selectPage = crudAllotProfitConfigService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            AllotProfitConfigResponse response = new AllotProfitConfigResponse();
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
    public AllotProfitConfigResponse get(Long id) {
        AllotProfitConfig profitParam = crudAllotProfitConfigService.selectById(id);
        // 数据权限
        CurrentUserHelper.checkPlatformThrow(profitParam.getPlatformUsername());
        // 响应
        AllotProfitConfigResponse response = new AllotProfitConfigResponse();
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
    @GlobalLock(key = DsAdminConstant.CPUS + "':apc-create'")
    public void create(AllotProfitConfigUpsertRequest request) {
        SystemUser currentUser = CurrentUserHelper.self();
        // 检查是否存在并插入数据
        LambdaQueryWrapper<AllotProfitConfig> queryWrapper = Wrappers.<AllotProfitConfig>lambdaQuery()
                .eq(AllotProfitConfig::getPlatformUsername, currentUser.getPlatformUsername())
                .eq(AllotProfitConfig::getAccountType, request.getAccountType())
                .eq(AllotProfitConfig::getProfitType, request.getProfitType())
                .eq(AllotProfitConfig::getProfitLevel, request.getProfitLevel())
                .eq(AllotProfitConfig::getMemberIdentityType, request.getMemberIdentityType())
                .eq(AllotProfitConfig::getMemberRankType, request.getMemberRankType());
        if (crudAllotProfitConfigService.exist(queryWrapper)) {
            throw new BizException(ExceptionEnum.RANK_PARAM_CREATE_ERROR, "该参数已存在");
        }
        AllotProfitConfig profitParam = new AllotProfitConfig();
        // 建议使用setter, 字段类型问题能在编译期发现
        BeanCopierUtil.copy(request, profitParam);
        profitParam.setPlatformUsername(currentUser.getPlatformUsername());
        profitParam.setPlatformNickname(currentUser.getPlatformNickname());
        profitParam.setState(StateEnum.ENABLE);
        crudAllotProfitConfigService.insert(profitParam);
    }

    /**
     * 更新
     */
    public void modify(AllotProfitConfigUpsertRequest request) {
        AllotProfitConfig profitParam = crudAllotProfitConfigService.selectById(request.getId());
        // 数据权限
        CurrentUserHelper.checkPlatformThrow(profitParam.getPlatformUsername());
        // 建议使用setter, 字段类型问题能在编译期发现
        BeanCopierUtil.copy(request, profitParam);
        crudAllotProfitConfigService.updateById(profitParam);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        AllotProfitConfig profitParam = crudAllotProfitConfigService.selectById(id);
        // 数据权限
        CurrentUserHelper.checkPlatformThrow(profitParam.getPlatformUsername());
        crudAllotProfitConfigService.deleteById(id);
    }

    /**
     * 修改状态
     */
    public void changeState(Long id, StateEnum stateEnum) {
        AllotProfitConfig profitParam = crudAllotProfitConfigService.selectById(id);
        CurrentUserHelper.checkPlatformThrow(profitParam.getPlatformUsername());
        if (stateEnum == profitParam.getState()) {
            throw new BizException(ExceptionEnum.STATE_COMMON_ERROR);
        }
        profitParam.setState(stateEnum);
        crudAllotProfitConfigService.updateById(profitParam);
    }
}