package com.wwj.domain.weather.service;

import com.wwj.domain.weather.model.Weather;

/**
 * 天气领域服务接口
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface WeatherDomainService {
    /**
     * 请求天气数据
     *
     * @param cityName 城市名称
     * @return 天气数据
     */
    Weather getWeatherByCityName(String cityName);

    /**
     * 更新天气数据
     *
     * @param weather 天气数据
     * @return 更新后的天气数据
     */
    Weather updateWeather(Weather weather);
}