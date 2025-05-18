package com.wwj.plugin.weather.service;

import com.wwj.plugin.weather.model.WeatherInfo;

import java.util.List;

/**
 * 天气服务接口
 * 提供天气查询相关功能
 */
public interface WeatherService {
    
    /**
     * 获取当前天气
     *
     * @param city 城市名称
     * @return 当前天气信息
     */
    WeatherInfo getCurrentWeather(String city);
    
    /**
     * 获取天气预报
     *
     * @param city 城市名称
     * @param days 预报天数
     * @return 天气预报列表
     */
    List<WeatherInfo> getWeatherForecast(String city, Integer days);
}