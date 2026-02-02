package com.poc.gemf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableCaching
@EnableEntityDefinedRegions(basePackages = "com.poc.gemf.entity")
// 1. Tell JPA to take ownership of the real repository
@EnableJpaRepositories(basePackages = "com.poc.gemf.repository")
// 2. Trick GemFire into looking at an empty/fake package.
// This prevents it from creating a conflicting bean for ProductRepository.
@EnableGemfireRepositories(basePackages = "com.poc.gemf.gemfirerepo")
public class GemfpocApplication {

	public static void main(String[] args) {
		SpringApplication.run(GemfpocApplication.class, args);
	}

}
