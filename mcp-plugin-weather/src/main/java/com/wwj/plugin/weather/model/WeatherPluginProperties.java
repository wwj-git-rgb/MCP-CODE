package com.wwj.plugin.weather.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 天气插件配置属性
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@ConfigurationProperties(prefix = "mcp.plugins.weather")
public class WeatherPluginProperties {
    /**
     * 是否启用天气插件
     */
    private boolean enabled = true;
    
    /**
     * API密钥
     */
    private String apiKey = "demo-api-key";
    
    /**
     * API基础URL
     */
    private String baseUrl = "https://api.example.com/weather";
    
    /**
     * 请求超时时间（毫秒）
     */
    private int timeout = 5000;
    
    /**
     * 缓存时间（秒）
     */
    private int cacheTtl = 3600;
    
    /**
     * 默认语言
     */
    private String language = "zh_CN";
    
    /**
     * 默认单位（metric/imperial）
     */
    private String units = "metric";
}