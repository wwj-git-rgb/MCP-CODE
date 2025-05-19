package com.wwj.spring.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * MCP配置属性
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "mcp")
public class MCPProperties {
    /**
     * 是否启用MCP
     */
    private boolean enabled = true;
    
    /**
     * 功能特性配置
     */
    private Features features = new Features();
    
    /**
     * 全局配置
     */
    private Map<String, Object> config = new HashMap<>();
    
    /**
     * 工具配置
     */
    private ToolConfig tools = new ToolConfig();
    
    /**
     * 功能特性配置
     */
    @Data
    public static class Features {
        /**
         * 天气特性配置
         */
        private Weather weather = new Weather();
        
        /**
         * 文档特性配置
         */
        private Documentation documentation = new Documentation();
    }
    
    /**
     * 天气特性配置
     */
    @Data
    public static class Weather {
        /**
         * 是否启用天气特性
         */
        private boolean enabled = true;
        
        /**
         * API密钥
         */
        private String apiKey = "demo-api-key";
        
        /**
         * 天气提供商
         */
        private String provider = "default";
        
        /**
         * API基础URL
         */
        private String baseUrl = "https://api.example.com/weather";
        
        /**
         * 缓存时间（秒）
         */
        private int cacheTtl = 3600;
    }
    
    /**
     * 文档特性配置
     */
    @Data
    public static class Documentation {
        /**
         * 是否启用文档特性
         */
        private boolean enabled = true;
        
        /**
         * 存储路径
         */
        private String storagePath = "/data/mcp/documents";
        
        /**
         * 最大文档大小（字节）
         */
        private long maxSize = 1024 * 1024 * 10; // 10MB
    }
    
    /**
     * 工具配置
     */
    @Data
    public static class ToolConfig {
        /**
         * 并发执行器线程数
         */
        private int executorThreads = 10;
        
        /**
         * 是否启用工具执行监控
         */
        private boolean enableMetrics = true;
        
        /**
         * 默认超时时间（毫秒）
         */
        private long defaultTimeoutMs = 30000;
    }
}