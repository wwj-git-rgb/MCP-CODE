package com.wwj.starter.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

/**
 * 文档自动配置类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "mcp.features.documentation", name = "enabled", havingValue = "true", matchIfMissing = true)
public class DocumentationAutoConfiguration {

    public DocumentationAutoConfiguration() {
        log.info("初始化文档自动配置");
    }
}