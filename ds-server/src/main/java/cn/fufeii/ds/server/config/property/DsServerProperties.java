package cn.fufeii.ds.server.config.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * admin项目配置取值器
 *
 * @author FuFei
 * @date 2022/4/5
 */
@Data
@ConfigurationProperties(prefix = "ds.admin")
@Configuration
public class DsServerProperties {

    /**
     * 启用API加密机制
     */
    private Boolean enableApiEncryption = false;

    /**
     * 启用API签名机制
     */
    private Boolean enableApiSignature = true;

}
