package com.wwj.test.spring;

import com.wwj.test.MCPTestContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MCP测试自动配置
 * <p>提供测试环境的自动配置支持</p>
 *
 * @author wenjie
 * @since 1.0.0
 */
@Configuration
public class MCPTestAutoConfiguration {

    /**
     * 创建MCP测试上下文Bean
     *
     * @return MCP测试上下文
     */
    @Bean
    @ConditionalOnMissingBean
    public MCPTestContext mcpTestContext() {
        return new MCPTestContext();
    }
}