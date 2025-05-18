package com.wwj.plugin.weather.controller;

import com.wwj.common.constants.McpConstants;
import com.wwj.common.result.Result;
import com.wwj.plugin.weather.model.WeatherInfo;
import com.wwj.plugin.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 天气控制器
 * 提供天气查询的RESTful API
 */
@Slf4j
@RestController
@RequestMapping("/api/weather")
public class WeatherController {

    private final WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 获取指定城市的当前天气
     * @param city 城市名称
     * @return 当前天气信息
     */
    @GetMapping("/current/{city}")
    public Result<WeatherInfo> getCurrentWeather(@PathVariable String city) {
        log.info("API - 获取城市[{}]的当前天气", city);
        try {
            WeatherInfo weatherInfo = weatherService.getCurrentWeather(city);
            return Result.success(weatherInfo);
        } catch (Exception e) {
            log.error("获取当前天气失败", e);
            return Result.failure(McpConstants.ERROR_INTERNAL, "获取天气信息失败: " + e.getMessage());
        }
    }

    /**
     * 获取指定城市的天气预报
     * @param city 城市名称
     * @param days 天数
     * @return 天气预报列表
     */
    @GetMapping("/forecast/{city}/{days}")
    public Result<List<WeatherInfo>> getWeatherForecast(@PathVariable String city, @PathVariable Integer days) {
        log.info("API - 获取城市[{}]的[{}]天天气预报", city, days);
        try {
            List<WeatherInfo> forecast = weatherService.getWeatherForecast(city, days);
            return Result.success(forecast);
        } catch (Exception e) {
            log.error("获取天气预报失败", e);
            return Result.failure(McpConstants.ERROR_INTERNAL, "获取天气预报失败: " + e.getMessage());
        }
    }
}