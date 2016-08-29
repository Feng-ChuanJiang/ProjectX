package com.cci.projectx.core;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.TimeUnit;


@Configuration
@EnableAsync
public class CacheConfig {

    @Bean
    @Qualifier("sessionCache")
    public Cache<String, Long> sessionCache() {
        Cache<String, Long> cache = CacheBuilder.newBuilder()
                .expireAfterAccess(1, TimeUnit.DAYS).build();
        return cache;
    }

}
