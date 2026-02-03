package com.poc.gemf.config;

import org.apache.geode.cache.GemFireCache;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.gemfire.cache.GemfireCacheManager;
import org.springframework.data.gemfire.client.ClientRegionFactoryBean;
import org.springframework.data.gemfire.mapping.MappingPdxSerializer;

import java.util.Collections;

@Configuration
public class GemFireConfig {

    @Bean
    public MappingPdxSerializer pdxSerializer() {
        MappingPdxSerializer serializer = new MappingPdxSerializer();
        serializer.setIncludeTypeFilters(type -> true);
        return serializer;
    }

    @Bean("Products")
    public ClientRegionFactoryBean<Object, Object> productsRegion(GemFireCache gemfireCache) {
        ClientRegionFactoryBean<Object, Object> region = new ClientRegionFactoryBean<>();
        region.setCache(gemfireCache);
        region.setShortcut(ClientRegionShortcut.PROXY);
        return region;
    }

    @Bean
    public CacheManager cacheManager(GemFireCache gemfireCache) {
        GemfireCacheManager cacheManager = new GemfireCacheManager();
        cacheManager.setCache(gemfireCache);
        cacheManager.setCacheNames(Collections.singleton("Products"));
        return cacheManager;
    }
}