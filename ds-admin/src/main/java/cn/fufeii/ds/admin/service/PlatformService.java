package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.PlatformQueryRequest;
import cn.fufeii.ds.admin.model.vo.request.PlatformUpsertRequest;
import cn.fufeii.ds.admin.model.vo.response.PlatformResponse;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.enumerate.biz.StateEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudPlatformService;
import cn.fufeii.ds.repository.entity.Platform;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 平台信息 Service
 *
 * @author FuFei
 */
@Service
public class PlatformService {

    @Autowired
    private CrudPlatformService crudPlatformService;

    /**
     * 分页查询
     */
    public IPage<PlatformResponse> page(PlatformQueryRequest pageParam, IPage<Platform> pageable) {
        LambdaQueryWrapper<Platform> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, Platform.class));
        CurrentUserHelper.setPlatformIfPossible(queryWrapper);
        IPage<Platform> selectPage = crudPlatformService.selectPage(queryWrapper, pageable);
        // 组装response对象返回
        return selectPage.convert(it -> {
            PlatformResponse response = new PlatformResponse();
            response.setId(it.getId());
            response.setUsername(it.getUsername());
            response.setNickname(it.getNickname());
            response.setSk(it.getSk());
            response.setWebhook(it.getWebhook());
            response.setState(it.getState().getMessage());
            response.setCreateDateTime(it.getCreateDateTime());
            return response;
        });
    }

    /**
     * 创建平台
     */
    public void create(PlatformUpsertRequest request) {
        // 查看平台是否已存在
        boolean present = crudPlatformService.selectOneOpt(Wrappers.<Platform>lambdaQuery().eq(Platform::getUsername, request.getUsername()))
                .isPresent();
        if (present) {
            throw new BizException(ExceptionEnum.PLATFORM_CREATE_ERROR, "平台已存在");
        }

        // 创建平台
        Platform platform = new Platform();
        platform.setUsername(request.getUsername());
        platform.setNickname(request.getNickname());
        platform.setWebhook(request.getWebhook());
        platform.setSk(RandomUtil.randomString(32));
        platform.setState(StateEnum.ENABLE);
        crudPlatformService.insert(platform);
    }

    /**
     * 修改状态
     */
    public void changeState(Long id, StateEnum stateEnum) {
        Platform platform = crudPlatformService.selectById(id);
        if (stateEnum == platform.getState()) {
            throw new BizException(ExceptionEnum.STATE_COMMON_ERROR);
        }
        platform.setState(stateEnum);
        crudPlatformService.updateById(platform);
    }


}