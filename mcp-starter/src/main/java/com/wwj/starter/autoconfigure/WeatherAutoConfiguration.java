package com.wwj.starter.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 天气自动配置类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherAutoConfiguration {

    /**
     * 天气缓存管理器
     */
    @Bean
    public CacheManager weatherCacheManager() {
        log.info("初始化天气缓存管理器");
        return new ConcurrentMapCacheManager("weather");
    }
}