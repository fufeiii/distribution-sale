package cn.fufeii.ds.server.security;

import cn.fufeii.ds.repository.entity.Platform;

/**
 * 当前平台 Holder
 *
 * @author FuFei
 * @date 2022/4/6
 */
public final class CurrentPlatformHolder {

    public static final ThreadLocal<Platform> PLATFORM_THREAD_LOCAL = new InheritableThreadLocal<>();

    public static Platform get() {
        return PLATFORM_THREAD_LOCAL.get();
    }

    public static void set(Platform platform) {
        PLATFORM_THREAD_LOCAL.set(platform);
    }

    public static void remove() {
        PLATFORM_THREAD_LOCAL.remove();
    }

}
