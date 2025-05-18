package com.wwj.infrastructure.weather.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient配置
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Configuration
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WebClientConfig {

    /**
     * 配置WebClient构建器
     *
     * @return WebClient.Builder实例
     */
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .exchangeStrategies(ExchangeStrategies.builder()
                        .codecs(configurer -> configurer
                                .defaultCodecs()
                                .maxInMemorySize(16 * 1024 * 1024))
                        .build());
    }
}
