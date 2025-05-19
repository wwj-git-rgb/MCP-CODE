package com.wwj.spring.autoconfigure;

import com.wwj.domain.core.tool.ToolRegistry;
import com.wwj.infrastructure.tool.SimpleToolRegistry;
import com.wwj.plugin.core.annotation.EnableMCPPlugins;
import com.wwj.spring.properties.MCPProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * MCP自动配置类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@EnableCaching
@EnableMCPPlugins
@EnableConfigurationProperties(MCPProperties.class)
@ComponentScan({"com.wwj.domain", "com.wwj.infrastructure", "com.wwj.application"})
@ConditionalOnProperty(prefix = "mcp", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class MCPAutoConfiguration {

    public MCPAutoConfiguration(MCPProperties properties) {
        log.info("初始化MCP自动配置");
        log.info("全局是否启用MCP: {}", properties.isEnabled());
        log.info("天气特性是否启用: {}", properties.getFeatures().getWeather().isEnabled());
        log.info("文档特性是否启用: {}", properties.getFeatures().getDocumentation().isEnabled());
    }
    
    /**
     * 工具注册表Bean
     */
    @Bean
    @ConditionalOnMissingBean
    public ToolRegistry toolRegistry() {
        log.info("创建默认工具注册表");
        return new SimpleToolRegistry();
    }
    
    /**
     * MCP任务执行器
     */
    @Bean
    @ConditionalOnMissingBean(name = "mcpTaskExecutor")
    public TaskExecutor mcpTaskExecutor(MCPProperties properties) {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(properties.getTools().getExecutorThreads());
        executor.setMaxPoolSize(properties.getTools().getExecutorThreads() * 2);
        executor.setQueueCapacity(50);
        executor.setThreadNamePrefix("mcp-worker-");
        executor.initialize();
        log.info("创建 MCP 任务执行器，线程数: {}", properties.getTools().getExecutorThreads());
        return executor;
    }
}