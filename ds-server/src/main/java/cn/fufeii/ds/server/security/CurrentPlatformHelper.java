package cn.fufeii.ds.server.security;

import cn.fufeii.ds.repository.entity.Platform;

/**
 * 当前平台 Helper
 *
 * @author FuFei
 * @date 2022/4/6
 */
public final class CurrentPlatformHelper {

    /**
     * 获取当前平台
     */
    public static Platform self() {
        return PlatformContextHolder.get();
    }

    /**
     * 获取username
     */
    public static String username() {
        return PlatformContextHolder.get().getUsername();
    }

}
