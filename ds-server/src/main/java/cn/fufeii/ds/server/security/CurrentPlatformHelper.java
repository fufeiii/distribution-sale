package cn.fufeii.ds.server.security;

import cn.fufeii.ds.common.enumerate.ExceptionEnum;
import cn.fufeii.ds.common.exception.BizException;
import cn.fufeii.ds.repository.entity.Platform;

import java.util.Objects;

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


    /**
     * 检查数据权限
     */
    public static void checkPlatformThrow(String dataPlatformUsername) {
        if (!Objects.equals(username(), dataPlatformUsername)) {
            throw new BizException(ExceptionEnum.NO_DATA_PERMISSION);
        }
    }

}
