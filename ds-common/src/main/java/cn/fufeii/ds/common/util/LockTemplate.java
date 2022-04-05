package cn.fufeii.ds.common.util;

import cn.fufeii.ds.common.constant.DsConstant;
import cn.hutool.core.util.StrUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 编程式写法
 * {@link cn.fufeii.ds.common.aop.GlobalLockAspect}
 *
 * @author FuFei
 * @date 2022/4/5
 */
@Component
public class LockTemplate {

    @Autowired
    private RedissonClient redissonClient;

    public void runWithLock(String key, Logger log, Runnable runnable) {
        this.execute(DsConstant.REDIS_GL_NAMESPACE + key, 0, log, runnable);
    }

    public void runWithTryLock(String key, long waitTime, Logger log, Runnable runnable) {
        this.execute(DsConstant.REDIS_GL_NAMESPACE + key, waitTime, log, runnable);
    }

    /**
     * 加锁执行代码逻辑
     *
     * @param glKey    锁名
     * @param waitTime 等待时间
     * @param log      日志器
     * @param runnable 执行逻辑
     */
    private void execute(String glKey, long waitTime, Logger log, Runnable runnable) {
        RLock rLock = redissonClient.getLock(glKey);
        boolean isLocked = false;
        try {
            // 加锁
            isLocked = rLock.tryLock(waitTime, TimeUnit.SECONDS);
            // 加锁失败抛出异常
            if (!isLocked) {
                throw new IllegalStateException(StrUtil.format("加锁失败，线程{}：{}", Thread.currentThread().getName(), glKey));
            }
            // 执行目标方法
            if (log != null) {
                log.debug("加锁成功：{}", glKey);
            }
            runnable.run();
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        } finally {
            // 加锁成功才解锁
            if (isLocked) {
                rLock.unlock();
                if (log != null) {
                    log.debug("解锁成功：{}", glKey);
                }
            }
        }
    }

}
