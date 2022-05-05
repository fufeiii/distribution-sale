package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.MemberRankConfigQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.MemberRankConfigUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.MemberRankConfigResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.common.util.LockTemplate;
import cn.fufeii.ds.repository.crud.CrudMemberRankConfigService;
import cn.fufeii.ds.repository.entity.MemberRankConfig;
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
public class MemberRankConfigService {

    @Autowired
    private CrudMemberRankConfigService crudMemberRankConfigService;
    @Autowired
    private LockTemplate lockTemplate;

    /**
     * 分页查询
     */
    public IPage<MemberRankConfigResponse> page(MemberRankConfigQueryRequest pageParam, IPage<MemberRankConfig> pageable) {
        LambdaQueryWrapper<MemberRankConfig> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, MemberRankConfig.class));
        CurrentUserHelper.setPlatformIfPossible(queryWrapper);
        IPage<MemberRankConfig> selectPage = crudMemberRankConfigService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            MemberRankConfigResponse response = new MemberRankConfigResponse();
            this.setResponse(it, response);
            return response;
        });
    }

    /**
     * 获取
     */
    public MemberRankConfigResponse get(Long id) {
        MemberRankConfig rankParam = crudMemberRankConfigService.selectById(id);
        CurrentUserHelper.checkPlatformThrow(rankParam.getPlatformUsername());
        MemberRankConfigResponse response = new MemberRankConfigResponse();
        this.setResponse(rankParam, response);
        return response;
    }

    /**
     * 通用响应设置
     */
    public void setResponse(MemberRankConfig rankParam, MemberRankConfigResponse response) {
        response.setId(rankParam.getId());
        response.setMemberRankType(rankParam.getMemberRankType().getMessage());
        response.setBeginPoints(rankParam.getBeginPoints());
        response.setEndPoints(rankParam.getEndPoints());
        response.setState(rankParam.getState().getMessage());
        response.setCreateDateTime(rankParam.getCreateDateTime());
        response.setUpdateDateTime(rankParam.getUpdateDateTime());
    }

    /**
     * 保存
     */
    public void create(MemberRankConfigUpsertRequest request) {
        if (request.getMemberRankType() == null) {
            throw new BizException(ExceptionEnum.API_FIELD_ERROR, "memberRankType不能为空");
        }
        this.checkPointsRange(request.getBeginPoints(), request.getEndPoints());
        SystemUser currentUser = CurrentUserHelper.self();
        // 手动加锁并执行逻辑
        lockTemplate.runWithLock(currentUser.getPlatformUsername() + ":rp-create", log, () -> {
            // 检查是否存在并插入数据
            LambdaQueryWrapper<MemberRankConfig> queryWrapper = Wrappers.<MemberRankConfig>lambdaQuery()
                    .eq(MemberRankConfig::getPlatformUsername, currentUser.getPlatformUsername())
                    .eq(MemberRankConfig::getMemberRankType, request.getMemberRankType());
            if (crudMemberRankConfigService.exist(queryWrapper)) {
                throw new BizException(ExceptionEnum.RANK_PARAM_CREATE_ERROR, "该参数已存在");
            }
            MemberRankConfig rankParam = new MemberRankConfig();
            // 建议使用setter, 字段类型问题能在编译期发现
            BeanCopierUtil.copy(request, rankParam);
            rankParam.setPlatformUsername(currentUser.getPlatformUsername());
            rankParam.setPlatformNickname(currentUser.getPlatformNickname());
            rankParam.setState(StateEnum.ENABLE);
            crudMemberRankConfigService.insert(rankParam);
        });
    }

    /**
     * 更新
     */
    public void modify(MemberRankConfigUpsertRequest request) {
        MemberRankConfig rankParam = crudMemberRankConfigService.selectById(request.getId());
        CurrentUserHelper.checkPlatformThrow(rankParam.getPlatformUsername());
        // 这个字段是不能改变的
        if (request.getMemberRankType() != null) {
            throw new BizException(ExceptionEnum.API_FIELD_ERROR, "memberRankType不能修改");
        }
        this.checkPointsRange(request.getBeginPoints(), request.getEndPoints());
        // 建议使用setter, 字段类型问题能在编译期发现
        BeanCopierUtil.copy(request, rankParam);
        crudMemberRankConfigService.updateById(rankParam);
    }

    /**
     * 检查分数
     */
    private void checkPointsRange(Integer begin, Integer end) {
        if (begin > end) {
            throw new BizException(ExceptionEnum.RANK_PARAM_CREATE_ERROR, "起始分数必须大于结束分数");
        }
        // FIXME 需要检查begin和end没有占用其他人的分段
        // where ((this.begin >= begin and this.begin <= end) or (this.end >= begin and this.end <= end)) and member_type != 自己
        // 这个条件为true时则校验不通过
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        MemberRankConfig rankParam = crudMemberRankConfigService.selectById(id);
        CurrentUserHelper.checkPlatformThrow(rankParam.getPlatformUsername());
        crudMemberRankConfigService.deleteById(id);
    }


    /**
     * 修改状态
     */
    public void changeState(Long id, StateEnum stateEnum) {
        MemberRankConfig rankParam = crudMemberRankConfigService.selectById(id);
        CurrentUserHelper.checkPlatformThrow(rankParam.getPlatformUsername());
        if (stateEnum == rankParam.getState()) {
            throw new BizException(ExceptionEnum.STATE_COMMON_ERROR);
        }
        rankParam.setState(stateEnum);
        crudMemberRankConfigService.updateById(rankParam);
    }


}