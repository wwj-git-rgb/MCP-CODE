package com.wwj.spring.autoconfigure;

import com.wwj.spring.properties.MCPProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

/**
 * 文档自动配置类
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Configuration(proxyBeanMethods = false)
@ConditionalOnProperty(prefix = "mcp.features.documentation", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureAfter(MCPAutoConfiguration.class)
public class DocumentationAutoConfiguration {

    public DocumentationAutoConfiguration() {
        log.info("初始化文档自动配置");
    }
    
    /**
     * 创建文档存储路径
     */
    @Bean
    public File documentationStorageDirectory(MCPProperties properties) {
        String storagePath = properties.getFeatures().getDocumentation().getStoragePath();
        File directory = new File(storagePath);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                log.info("创建文档存储目录: {}", storagePath);
            } else {
                log.warn("无法创建文档存储目录: {}", storagePath);
            }
        } else {
            log.info("文档存储目录已存在: {}", storagePath);
        }
        return directory;
    }
}