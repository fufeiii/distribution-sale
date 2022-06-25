package cn.fufeii.ds.server.config;

import cn.fufeii.ds.repository.config.MpAbstractTenantLineHandler;
import cn.fufeii.ds.server.security.CurrentPlatformHelper;
import org.springframework.stereotype.Component;

/**
 * @author FuFei
 */
@Component
public class MpServerTenantLineHandler extends MpAbstractTenantLineHandler {

    @Override
    protected boolean canIgnorePlatformUsername() {
        // server所有接口都是面像单个平台的，所以不能忽略
        return false;
    }

    @Override
    protected String getPlatformUsername() {
        return CurrentPlatformHelper.username();
    }

}
