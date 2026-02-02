package com.poc.gemf.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI gemfirePocOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("GemFire POC API")
                        .description("Spring Boot 2.7.x application with GemFire Cache, MySQL, and AOP Logging.")
                        .version("v1.0.0")
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")));
    }
}
