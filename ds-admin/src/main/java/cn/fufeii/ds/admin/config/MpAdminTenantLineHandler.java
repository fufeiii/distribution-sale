package cn.fufeii.ds.admin.config;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.admin.security.CurrentUserHelper;
import cn.fufeii.ds.repository.config.MpAbstractTenantLineHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author FuFei
 */
@Component
public class MpAdminTenantLineHandler extends MpAbstractTenantLineHandler {

    @Override
    protected boolean canIgnorePlatformUsername() {
        if (CurrentUserHelper.existAuthentication()) {
            return CurrentUserHelper.isAdmin();
        }
        // 如果SystemUser为null，还需要查询数据库，则判断是否为login接口
        // 因为登录的时候是没办法通过CurrentUserHelper去获取当前平台
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        return DsAdminConstant.LOGIN_URL.equals(attributes.getRequest().getServletPath());
    }

    @Override
    protected String getPlatformUsername() {
        return CurrentUserHelper.platformUsername();
    }

}
