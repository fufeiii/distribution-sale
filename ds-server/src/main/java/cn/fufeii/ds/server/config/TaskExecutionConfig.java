package cn.fufeii.ds.server.config;

import org.springframework.boot.task.TaskExecutorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步任务线程池额外配置
 *
 * @author FuFei
 * @date 2022/3/12
 */
@Configuration
public class TaskExecutionConfig {

    @Bean
    public TaskExecutorCustomizer taskExecutorCustomizer() {
        return taskExecutor -> taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

}
