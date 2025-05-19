package com.wwj.spring.autoconfigure;

import com.wwj.domain.core.tool.ToolRegistry;
import com.wwj.spring.properties.MCPProperties;
import com.wwj.spring.tool.ToolExecutionMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 工具自动配置类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@AutoConfigureAfter(MCPAutoConfiguration.class)
public class ToolAutoConfiguration {

    /**
     * 工具执行监控
     */
    @Bean
    @ConditionalOnProperty(prefix = "mcp.tools", name = "enable-metrics", havingValue = "true", matchIfMissing = true)
    @ConditionalOnClass(name = "io.micrometer.core.instrument.MeterRegistry")
    @ConditionalOnMissingBean
    public ToolExecutionMetrics toolExecutionMetrics(ToolRegistry toolRegistry, MCPProperties properties) {
        log.info("初始化工具执行监控");
        return new ToolExecutionMetrics(toolRegistry);
    }
}