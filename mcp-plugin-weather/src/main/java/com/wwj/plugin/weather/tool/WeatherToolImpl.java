package com.wwj.plugin.weather.tool;

import com.wwj.plugin.api.annotation.Tool;
import com.wwj.plugin.api.weather.WeatherPlugin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 天气工具实现
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Component
@Tool(name = "weather", description = "天气查询工具", author = "MCP Team", tags = {"weather", "tool"})
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.plugins.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherToolImpl {

    private final WeatherPlugin weatherPlugin;

    /**
     * 获取指定城市的天气信息
     *
     * @param city 城市名称
     * @return 天气信息字符串
     */
    @Tool.Action(name = "getWeather", description = "获取城市天气")
    public String getWeather(String city) {
        log.info("天气工具查询城市[{}]天气", city);
        return weatherPlugin.getWeather(city);
    }

    /**
     * 获取指定城市的天气预报
     *
     * @param city 城市名称
     * @param days 天数
     * @return 天气预报字符串
     */
    @Tool.Action(name = "getForecast", description = "获取城市天气预报")
    public String getForecast(String city, int days) {
        log.info("天气工具查询城市[{}]未来{}\u5929天气预报", city, days);
        return weatherPlugin.getForecast(city, days);
    }
}