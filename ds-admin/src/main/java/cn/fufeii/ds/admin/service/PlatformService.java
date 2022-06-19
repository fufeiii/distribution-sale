package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.config.property.DsAdminProperties;
import cn.fufeii.ds.admin.model.vo.request.PlatformQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.PlatformUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.PlatformResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.MemberRankTypeEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudMemberRankConfigService;
import cn.fufeii.ds.repository.crud.CrudPlatformService;
import cn.fufeii.ds.repository.entity.MemberRankConfig;
import cn.fufeii.ds.repository.entity.Platform;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 平台信息 Service
 *
 * @author FuFei
 */
@Service
public class PlatformService {
    @Autowired
    private DsAdminProperties dsAdminProperties;
    @Autowired
    private CrudPlatformService crudPlatformService;
    @Autowired
    private CrudMemberRankConfigService crudMemberRankConfigService;

    /**
     * 分页查询
     */
    public IPage<PlatformResponse> page(PlatformQueryRequest pageParam, IPage<Platform> pageable) {
        LambdaQueryWrapper<Platform> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, Platform.class));
        if (CurrentUserHelper.isNotAdmin()) {
            queryWrapper.eq(Platform::getUsername, CurrentUserHelper.platformUsername());
        }
        IPage<Platform> selectPage = crudPlatformService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            PlatformResponse response = new PlatformResponse();
            response.setId(it.getId());
            response.setUsername(it.getUsername());
            response.setNickname(it.getNickname());
            response.setSk(it.getSk());
            response.setState(it.getState().getMessage());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

    /**
     * 创建平台
     */
    @Transactional
    public void create(PlatformUpsertRequest request) {
        // 查看平台是否已存在
        boolean present = crudPlatformService.selectOneOptional(Wrappers.<Platform>lambdaQuery().eq(Platform::getUsername, request.getUsername()))
                .isPresent();
        if (present) {
            throw BizException.client("平台已存在");
        }

        // 创建平台
        Platform platform = new Platform();
        platform.setUsername(request.getUsername());
        platform.setNickname(request.getNickname());
        platform.setNotifyUrl(request.getNotifyUrl());
        platform.setSk(RandomUtil.randomString(32));
        platform.setState(StateEnum.ENABLE);
        platform = crudPlatformService.insert(platform);

        // 创建段位配置
        final int step = dsAdminProperties.getMemberRankTypeStep();
        int beginPoints = 0;
        for (MemberRankTypeEnum memberRankTypeEnum : MemberRankTypeEnum.values()) {
            MemberRankConfig memberRankConfig = new MemberRankConfig();
            memberRankConfig.setPlatformUsername(platform.getUsername());
            memberRankConfig.setPlatformNickname(platform.getNickname());
            memberRankConfig.setMemberRankType(memberRankTypeEnum);
            memberRankConfig.setBeginPoints(beginPoints);
            memberRankConfig.setState(StateEnum.ENABLE);
            // 初始化下一次的参数范围
            int endPoints = beginPoints + step;
            beginPoints = endPoints + 1;
            memberRankConfig.setEndPoints(endPoints);
            crudMemberRankConfigService.insert(memberRankConfig);
        }

    }

    /**
     * 修改状态
     */
    public void changeState(Long id, StateEnum stateEnum) {
        Platform platform = crudPlatformService.selectById(id);
        if (stateEnum == platform.getState()) {
            throw new BizException(ExceptionEnum.UPDATE_STATE_REPEATEDLY);
        }
        platform.setState(stateEnum);
        crudPlatformService.updateById(platform);
    }


}