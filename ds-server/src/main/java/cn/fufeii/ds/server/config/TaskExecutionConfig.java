package cn.fufeii.ds.server.config;

import com.alibaba.ttl.TtlRunnable;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务线程池额外配置
 *
 * @author FuFei
 * @date 2022/3/12
 */
@Configuration
public class TaskExecutionConfig {

    /**
     * 配置spring任务线程池
     * 设置拒绝策略
     * 设置ttl的包装器
     */
    @Bean
    public TaskExecutorCustomizer taskExecutorCustomizer() {
        return taskExecutor -> {
            taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
            taskExecutor.setTaskDecorator(TtlRunnable::get);
        };
    }


    @Bean
    public AsyncConfigurer asyncConfigurer() {
        return new AsyncConfigurer() {
            @Override
            public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
                return new SimpleAsyncUncaughtExceptionHandler();
            }
        };
    }

}
