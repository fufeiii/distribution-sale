package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.RankParamQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.RankParamUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.RankParamResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudRankParamService;
import cn.fufeii.ds.repository.entity.RankParam;
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
public class RankParamService {

    @Autowired
    private CrudRankParamService crudRankParamService;

    /**
     * 分页查询
     */
    public IPage<RankParamResponse> page(RankParamQueryRequest pageParam, IPage<RankParam> pageable) {
        LambdaQueryWrapper<RankParam> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, RankParam.class));
        CurrentUserHelper.setPlatformIfPossible(queryWrapper);
        IPage<RankParam> selectPage = crudRankParamService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            RankParamResponse response = new RankParamResponse();
            response.setId(it.getId());
            response.setPlatformUsername(it.getPlatformUsername());
            response.setPlatformNickname(it.getPlatformNickname());
            response.setMemberRankType(it.getMemberRankType().getMessage());
            response.setBeginPoints(it.getBeginPoints());
            response.setEndPoints(it.getEndPoints());
            response.setCreateDateTime(it.getCreateDateTime());
            response.setUpdateDateTime(it.getUpdateDateTime());
            return response;
        });
    }

    /**
     * 获取
     */
    public RankParamResponse get(Long id) {
        RankParam rankParam = crudRankParamService.selectById(id);
        CurrentUserHelper.checkPlatformThrow(rankParam.getPlatformUsername());
        RankParamResponse response = new RankParamResponse();
        response.setId(rankParam.getId());
        response.setMemberRankType(rankParam.getMemberRankType().getMessage());
        response.setBeginPoints(rankParam.getBeginPoints());
        response.setEndPoints(rankParam.getEndPoints());
        response.setCreateDateTime(rankParam.getCreateDateTime());
        response.setUpdateDateTime(rankParam.getUpdateDateTime());
        return response;
    }

    /**
     * 保存
     */
    public void create(RankParamUpsertRequest request) {
        this.checkPointsRange(request.getBeginPoints(), request.getEndPoints());
        RankParam rankParam = new RankParam();
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(request, rankParam);
        SystemUser currentUser = CurrentUserHelper.self();
        rankParam.setPlatformUsername(currentUser.getPlatformUsername());
        rankParam.setPlatformNickname(currentUser.getPlatformNickname());
        crudRankParamService.insert(rankParam);
    }

    /**
     * 更新
     */
    public void modify(RankParamUpsertRequest request) {
        RankParam rankParam = crudRankParamService.selectById(request.getId());
        CurrentUserHelper.checkPlatformThrow(rankParam.getPlatformUsername());
        // 这个字段是不能改变的
        if (request.getMemberRankType() != null) {
            throw new BizException(ExceptionEnum.API_PARAM_ERROR, "memberRankType不能修改");
        }
        this.checkPointsRange(request.getBeginPoints(), request.getEndPoints());
        // 建议使用setter，字段类型问题能在编译期发现
        BeanCopierUtil.copy(request, rankParam);
        crudRankParamService.updateById(rankParam);
    }

    /**
     * 删除
     */
    public void remove(Long id) {
        RankParam rankParam = crudRankParamService.selectById(id);
        CurrentUserHelper.checkPlatformThrow(rankParam.getPlatformUsername());
        crudRankParamService.deleteById(id);
    }


    /**
     * 检查分数
     */
    private void checkPointsRange(Integer begin, Integer end) {
        if (begin > end) {
            throw new BizException(ExceptionEnum.RANK_PARAM_CREATE_ERROR, "起始分数必须大于结束分数");
        }
    }

}