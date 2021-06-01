package com.demo.config.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;

/**
 * swagger配置
 *
 * @author zhang
 * @date 2021-6-1
 */
@Configuration
public class Swagger2Config {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.demo.web.control"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("ZhangLong", "", "3269199948@qq.com");
        return new ApiInfo(
                "试岗Demo",
                "试岗用的一个Demo，没什么好说的了...",
                "1.0",
                "..",
                contact,
                "..",
                "..", Collections.emptyList());
    }
}
