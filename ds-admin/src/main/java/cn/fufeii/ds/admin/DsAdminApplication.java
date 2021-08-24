package cn.fufeii.ds.admin;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author FuFei
 * @date 2021/8/22
 */
@SpringBootApplication(scanBasePackages = "cn.fufeii.ds")
@Slf4j
public class DsAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(DsAdminApplication.class);
        log.info("ds-admin is success!");
    }

}