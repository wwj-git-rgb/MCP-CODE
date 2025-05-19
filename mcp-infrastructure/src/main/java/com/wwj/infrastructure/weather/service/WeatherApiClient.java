package com.wwj.infrastructure.weather.service;

import com.wwj.domain.weather.model.Weather;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * 天气API客户端
 *
 * @author wenjie
 * @since 1.0.0
 */
@Component
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public interface WeatherApiClient {
    /**
     * 获取城市的天气数据
     *
     * @param cityName 城市名称
     * @return 天气数据
     */
    Weather getWeatherForCity(String cityName);
}