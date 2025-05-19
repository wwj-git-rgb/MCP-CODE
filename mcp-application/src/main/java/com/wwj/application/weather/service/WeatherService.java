package com.wwj.application.weather.service;

import com.wwj.application.weather.dto.WeatherDTO;

/**
 * 天气应用服务接口
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface WeatherService {
    /**
     * 获取指定城市的天气数据
     *
     * @param cityName 城市名称
     * @return 天气数据DTO
     */
    WeatherDTO getWeatherByCity(String cityName);

    /**
     * 更新天气数据
     *
     * @param weatherDTO 天气数据DTO
     * @return 更新后的天气数据DTO
     */
    WeatherDTO updateWeather(WeatherDTO weatherDTO);
}