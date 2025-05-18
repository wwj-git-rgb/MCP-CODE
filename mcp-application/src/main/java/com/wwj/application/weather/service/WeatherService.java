package com.wwj.application.weather.service;

import com.wwj.application.weather.dto.WeatherDTO;

/**
 * 天气应用服务接口
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public interface WeatherService {

    /**
     * 获取城市天气信息
     *
     * @param cityName 城市名称
     * @return 天气信息数据传输对象
     */
    WeatherDTO getWeatherByCity(String cityName);

    /**
     * 根据城市编码获取天气信息
     *
     * @param cityCode 城市编码
     * @return 天气信息数据传输对象
     */
    WeatherDTO getWeatherByCityCode(String cityCode);

    /**
     * 强制刷新城市天气信息
     *
     * @param cityName 城市名称
     * @return 天气信息数据传输对象
     */
    WeatherDTO refreshWeather(String cityName);
}
