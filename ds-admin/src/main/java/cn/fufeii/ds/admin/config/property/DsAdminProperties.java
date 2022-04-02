package cn.fufeii.ds.admin.config.property;

import lombok.Data;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * admin项目配置取值器
 *
 * @author FuFei
 * @date 2022/3/26
 */
@Data
@ConfigurationProperties(prefix = "ds.admin")
@Configuration
public class DsAdminProperties implements InitializingBean {

    /**
     * jwt签名密钥
     */
    private String jwtSignKey = "";

    /**
     * jwt签名有效时常
     */
    private Duration jwtSignTtl = Duration.ofHours(1);

    /**
     * 登录错误次数限制
     */
    private Integer loginErrorLimit = 5;


    @Override
    public void afterPropertiesSet() throws Exception {
        if (jwtSignKey.length() != 32) {
            throw new IllegalArgumentException("jwt签名密钥长度应为32个字符");
        }
    }

}
