package cn.fufeii.ds.admin.config;

import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.repository.config.MpAbstractTenantLineHandler;
import org.springframework.stereotype.Component;

/**
 * @author FuFei
 */
@Component
public class MpAdminTenantLineHandler extends MpAbstractTenantLineHandler {

    @Override
    protected boolean canIgnorePlatformUsername() {
        return CurrentUserHelper.isAdmin();
    }

    @Override
    protected String getPlatformUsername() {
        return CurrentUserHelper.platformUsername();
    }

}
