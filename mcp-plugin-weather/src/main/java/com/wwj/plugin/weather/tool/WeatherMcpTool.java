package com.wwj.plugin.weather.tool;

import com.wwj.plugin.weather.model.WeatherInfo;
import com.wwj.plugin.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 天气MCP工具类
 * 提供天气查询功能的MCP工具
 */
@Slf4j
@Component
public class WeatherMcpTool {

    private final WeatherService weatherService;

    @Autowired
    public WeatherMcpTool(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 获取当前天气
     *
     * @param city 城市名称
     * @return 当前天气信息
     */
    public WeatherInfo getCurrentWeather(String city) {
        log.info("获取城市[{}]的当前天气", city);
        
        if (!StringUtils.hasText(city)) {
            throw new IllegalArgumentException("城市名称不能为空");
        }
        
        return weatherService.getCurrentWeather(city);
    }

    /**
     * 获取天气预报
     *
     * @param city 城市名称
     * @param days 预报天数
     * @return 天气预报列表
     */
    public List<WeatherInfo> getWeatherForecast(String city, Integer days) {
        log.info("获取城市[{}]的[{}]天天气预报", city, days);
        
        if (!StringUtils.hasText(city)) {
            throw new IllegalArgumentException("城市名称不能为空");
        }
        
        if (days == null || days <= 0) {
            days = 3; // 默认3天
            log.info("预报天数无效，使用默认值：{}", days);
        } else if (days > 7) {
            days = 7; // 最多7天
            log.info("预报天数超出限制，使用最大值：{}", days);
        }
        
        return weatherService.getWeatherForecast(city, days);
    }
}