package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.ProfitParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.ProfitParamUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.ProfitParamResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.common.util.LockTemplate;
import cn.fufeii.ds.repository.crud.CrudProfitParamService;
import cn.fufeii.ds.repository.entity.ProfitParam;
import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分润参数 Service
 *
 * @author FuFei
 */
@Slf4j
@Service
public class ProfitParamService {

    @Autowired
    private CrudProfitParamService crudProfitParamService;
    @Autowired
    private LockTemplate lockTemplate;

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
        SystemUser currentUser = CurrentUserHelper.self();
        // 手动加锁并执行逻辑
        lockTemplate.runWithLock(currentUser.getPlatformUsername() + ":pp-create", log, () -> {
            // 检查是否存在并插入数据
            LambdaQueryWrapper<ProfitParam> queryWrapper = Wrappers.<ProfitParam>lambdaQuery()
                    .eq(ProfitParam::getPlatformUsername, currentUser.getPlatformUsername())
                    .eq(ProfitParam::getAccountType, request.getAccountType())
                    .eq(ProfitParam::getProfitType, request.getProfitType())
                    .eq(ProfitParam::getCalculateMode, request.getCalculateMode())
                    .eq(ProfitParam::getProfitLevel, request.getProfitLevel())
                    .eq(ProfitParam::getMemberIdentityType, request.getMemberIdentityType())
                    .eq(ProfitParam::getMemberRankType, request.getMemberRankType());
            if (crudProfitParamService.exist(queryWrapper)) {
                throw new BizException(ExceptionEnum.RANK_PARAM_CREATE_ERROR, "该参数已存在");
            }
            ProfitParam profitParam = new ProfitParam();
            // 建议使用setter，字段类型问题能在编译期发现
            BeanCopierUtil.copy(request, profitParam);
            profitParam.setPlatformUsername(currentUser.getPlatformUsername());
            profitParam.setPlatformNickname(currentUser.getPlatformNickname());
            crudProfitParamService.insert(profitParam);
        });
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
        crudProfitParamService.updateById(profitParam);
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

    /**
     * 修改状态
     */
    public void changeState(Long id, StateEnum stateEnum) {
        ProfitParam profitParam = crudProfitParamService.selectById(id);
        if (stateEnum == profitParam.getState()) {
            throw new BizException(ExceptionEnum.STATE_COMMON_ERROR);
        }
        profitParam.setState(stateEnum);
        crudProfitParamService.updateById(profitParam);
    }
}