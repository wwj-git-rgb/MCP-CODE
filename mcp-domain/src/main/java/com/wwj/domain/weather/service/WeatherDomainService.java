package com.wwj.domain.weather.service;

import com.wwj.domain.weather.model.Weather;

/**
 * 天气服务领域接口
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public interface WeatherDomainService {

    /**
     * 获取指定城市的天气信息
     *
     * @param cityName 城市名称
     * @return 天气信息
     */
    Weather getWeatherByCity(String cityName);

    /**
     * 获取指定城市编码的天气信息
     *
     * @param cityCode 城市编码
     * @return 天气信息
     */
    Weather getWeatherByCityCode(String cityCode);

    /**
     * 从缓存中获取指定城市的天气信息
     *
     * @param cityName 城市名称
     * @return 天气信息（如果缓存中存在）
     */
    Weather getWeatherFromCache(String cityName);

    /**
     * 从外部API源获取指定城市的天气信息
     *
     * @param cityName 城市名称
     * @return 天气信息
     */
    Weather getWeatherFromApi(String cityName);

    /**
     * 更新指定城市的天气信息
     *
     * @param weather 天气信息
     * @return 更新后的天气信息
     */
    Weather updateWeather(Weather weather);

    /**
     * 判断城市名称是否有效
     *
     * @param cityName 城市名称
     * @return 是否有效
     */
    boolean isValidCity(String cityName);
}
