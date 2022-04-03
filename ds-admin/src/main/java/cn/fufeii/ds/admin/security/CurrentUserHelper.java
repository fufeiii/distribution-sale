package cn.fufeii.ds.admin.security;

import cn.fufeii.ds.admin.config.constant.DsAdminConstant;
import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.config.CurrentPlatformHelper;
import cn.fufeii.ds.repository.entity.SystemUser;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 当前用户 Helper
 *
 * @author FuFei
 * @date 2022/4/2
 */
public class CurrentUserHelper {

    /**
     * 当前用户
     */
    public static SystemUser self() {
        return (SystemUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    /**
     * 用户不是超管
     */
    public static boolean isNotAdmin() {
        return !isAdmin(self());
    }

    /**
     * 用户是为超管
     */
    public static boolean isAdmin() {
        return isAdmin(self());
    }

    /**
     * 用户是为超管
     */
    public static boolean isAdmin(SystemUser systemUser) {
        return DsAdminConstant.ADMIN_USERNAME.equals(systemUser.getUsername());
    }

    /**
     * 如果是超管就抛出异常
     */
    public static void isAdminThrow() {
        if (CurrentUserHelper.isAdmin()) {
            throw new BizException(ExceptionEnum.API_ADMIN_DENY_ERROR);
        }
    }

    /**
     * 当前用户的平台
     */
    public static String platformUsername() {
        return self().getPlatformUsername();
    }

    /**
     * 检查数据权限
     */
    public static void checkPlatformThrow(String dataPlatformUsername) {
        CurrentPlatformHelper.checkPlatformThrow(platformUsername(), dataPlatformUsername);
    }

    /**
     * 设置平台标识到查询条件中
     */
    public static <T> void setPlatformIfPossible(LambdaQueryWrapper<T> queryWrapper) {
        if (isNotAdmin()) {
            CurrentPlatformHelper.setPlatform(queryWrapper, platformUsername());
        }
    }

}
