package com.wwj.plugin.weather;

import com.wwj.plugin.core.McpPlugin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 天气插件
 * 实现了McpPlugin接口，提供天气服务的插件功能
 */
@Slf4j
@Component
public class WeatherPlugin implements McpPlugin {

    private static final String PLUGIN_ID = "com.wwj.weather";
    private static final String PLUGIN_NAME = "天气服务";
    private static final String PLUGIN_VERSION = "1.0.0";
    private static final String PLUGIN_DESCRIPTION = "提供天气查询功能，包括当前天气和天气预报";
    private static final String PLUGIN_AUTHOR = "MCP开发团队";

    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public String getVersion() {
        return PLUGIN_VERSION;
    }

    @Override
    public String getDescription() {
        return PLUGIN_DESCRIPTION;
    }

    @Override
    public String getAuthor() {
        return PLUGIN_AUTHOR;
    }

    @Override
    public void initialize() {
        log.info("天气插件初始化");
    }

    @Override
    public void destroy() {
        log.info("天气插件停止");
    }
}