package cn.fufeii.ds.admin.service;

import cn.fufeii.ds.admin.model.vo.request.PlatformQueryRequest;
import cn.fufeii.ds.admin.model.vo.response.PlatformResponse;
import cn.fufeii.ds.common.util.BeanCopierUtil;
import cn.fufeii.ds.repository.crud.CrudPlatformService;
import cn.fufeii.ds.repository.entity.Platform;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
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
        Wrapper<Platform> queryWrapper = Wrappers.lambdaQuery(BeanCopierUtil.copy(pageParam, Platform.class));
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

}