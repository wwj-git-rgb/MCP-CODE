package com.wwj.starter.autoconfigure;

import com.wwj.plugin.core.annotation.EnableMCPPlugins;
import com.wwj.starter.properties.MCPProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * MCP自动配置类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableCaching
@EnableMCPPlugins
@EnableConfigurationProperties(MCPProperties.class)
@ComponentScan({"com.wwj.domain", "com.wwj.infrastructure", "com.wwj.application"})
@ConditionalOnProperty(prefix = "mcp", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MCPAutoConfiguration {

    public MCPAutoConfiguration(MCPProperties properties) {
        log.info("初始化MCP自动配置");
        log.info("全局是否启用MCP: {}", properties.isEnabled());
        log.info("天气特性是否启用: {}", properties.getFeatures().getWeather().isEnabled());
        log.info("文档特性是否启用: {}", properties.getFeatures().getDocumentation().isEnabled());
    }
}