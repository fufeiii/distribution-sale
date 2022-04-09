package cn.fufeii.ds.server.security;

import cn.fufeii.ds.repository.entity.Platform;

/**
 * 当前平台 Helper
 *
 * @author FuFei
 * @date 2022/4/6
 */
public final class CurrentPlatformHelper {

    public static Platform self() {
        return PlatformContextHolder.get();
    }

    public static String username() {
        return PlatformContextHolder.get().getUsername();
    }


}
