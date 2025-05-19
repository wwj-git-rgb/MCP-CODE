package com.wwj.plugin.api.weather;

/**
 * 天气插件接口
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface WeatherPlugin {
    /**
     * 获取城市天气
     *
     * @param city 城市名称
     * @return 天气信息字符串
     */
    String getWeather(String city);
    
    /**
     * 获取城市未来天气预报
     *
     * @param city 城市名称
     * @param days 天数，1-7
     * @return 天气预报信息字符串
     */
    String getForecast(String city, int days);
}