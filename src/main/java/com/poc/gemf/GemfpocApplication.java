package com.poc.gemf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.poc.gemf.repository")
@EnableGemfireRepositories(basePackages = "com.poc.gemf.gemfirerepo")
@ClientCacheApplication(name = "GemfpocClient")
public class GemfpocApplication {

    public static void main(String[] args) {
        SpringApplication.run(GemfpocApplication.class, args);
    }
}