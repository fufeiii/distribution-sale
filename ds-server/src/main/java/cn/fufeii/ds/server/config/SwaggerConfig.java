package cn.fufeii.ds.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * swagger2 openApi3
 *
 * @author FuFei
 * @date 2021/9/3
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(new ApiInfoBuilder()
                        .title("Ds-Server Api Doc")
                        .description("服务端接口文档")
                        .termsOfServiceUrl("https://fufeii.cn")
                        .contact(new Contact("fufei", "https://fufeii.com", "fufei.@mail.com"))
                        .version("1.0.0")
                        .build())
                .groupName("管理后台")
                .select()
                .apis(RequestHandlerSelectors.basePackage("cn.fufeii.ds.server.controller"))
                .paths(PathSelectors.any())
                .build();
    }

}
