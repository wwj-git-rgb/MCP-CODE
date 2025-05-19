package com.wwj.plugin.core.config;

import com.wwj.plugin.core.loader.PluginLoader;
import com.wwj.plugin.core.properties.MCPPluginProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP插件自动配置类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MCPPluginProperties.class)
@ConditionalOnProperty(prefix = "mcp.plugins", name = "enabled", havingValue = "true", matchIfMissing = true)
public class MCPPluginAutoConfiguration {

    @Bean
    public PluginLoader pluginLoader(MCPPluginProperties properties) {
        log.info("初始化MCP插件加载器");
        return new PluginLoader(properties);
    }
}