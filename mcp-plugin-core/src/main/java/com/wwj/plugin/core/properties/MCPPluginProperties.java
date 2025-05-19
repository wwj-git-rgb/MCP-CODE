package com.wwj.plugin.core.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * MCP插件配置属性
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "mcp.plugins")
public class MCPPluginProperties {
    /**
     * 是否启用插件支持
     */
    private boolean enabled = true;
    
    /**
     * 插件扫描路径
     */
    private List<String> scanPaths = new ArrayList<>();
    
    /**
     * 是否支持热插拉
     */
    private boolean hotSwap = false;
    
    /**
     * 插件配置列表
     */
    private List<PluginConfig> plugins = new ArrayList<>();
    
    /**
     * 插件配置
     */
    @Data
    public static class PluginConfig {
        /**
         * 插件名称
         */
        private String name;
        
        /**
         * 是否启用
         */
        private boolean enabled = true;
        
        /**
         * 插件配置项
         */
        private Object config;
    }
}