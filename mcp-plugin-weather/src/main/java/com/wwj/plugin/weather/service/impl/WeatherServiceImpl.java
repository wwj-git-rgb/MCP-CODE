package com.wwj.plugin.weather.service.impl;

import com.wwj.plugin.weather.model.WeatherInfo;
import com.wwj.plugin.weather.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 天气服务实现类
 * 这是一个模拟实现，实际项目中应该调用真实的天气API
 */
@Slf4j
@Service
public class WeatherServiceImpl implements WeatherService {

    // 随机数生成器，用于模拟随机天气数据
    private final Random random = new Random();
    
    // 天气类型
    private final String[] weatherTypes = {
            "晴", "多云", "阴", "小雨", "中雨", "大雨", "暴雨", "雷阵雨", "小雪", "中雪", "大雪"
    };
    
    // 风向
    private final String[] windDirections = {
            "东风", "南风", "西风", "北风", "东北风", "东南风", "西南风", "西北风"
    };

    @Override
    public WeatherInfo getCurrentWeather(String city) {
        log.info("获取城市[{}]的当前天气", city);
        
        // 模拟延迟
        simulateDelay();
        
        // 创建并返回模拟的天气数据
        return createMockWeatherInfo(city, LocalDateTime.now());
    }

    @Override
    public List<WeatherInfo> getWeatherForecast(String city, Integer days) {
        log.info("获取城市[{}]的[{}]天天气预报", city, days);
        
        // 参数校验
        if (days == null || days <= 0) {
            days = 3; // 默认3天
        } else if (days > 7) {
            days = 7; // 最多7天
        }
        
        // 模拟延迟
        simulateDelay();
        
        // 创建天气预报列表
        List<WeatherInfo> forecast = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();
        
        // 生成指定天数的天气预报
        for (int i = 0; i < days; i++) {
            LocalDate forecastDate = currentDate.plusDays(i);
            LocalDateTime forecastDateTime = forecastDate.atTime(12, 0); // 中午12点
            
            WeatherInfo weatherInfo = createMockWeatherInfo(city, forecastDateTime);
            forecast.add(weatherInfo);
        }
        
        return forecast;
    }
    
    /**
     * 创建模拟的天气信息
     *
     * @param city 城市名称
     * @param dateTime 日期时间
     * @return 模拟的天气信息
     */
    private WeatherInfo createMockWeatherInfo(String city, LocalDateTime dateTime) {
        // 随机生成天气数据
        String weatherType = weatherTypes[random.nextInt(weatherTypes.length)];
        String windDirection = windDirections[random.nextInt(windDirections.length)];
        
        int temperature = random.nextInt(40) - 10; // -10°C 到 30°C
        int humidity = random.nextInt(101); // 0% 到 100%
        int windSpeed = random.nextInt(30); // 0 到 29 km/h
        
        // 根据天气类型调整温度
        if (weatherType.contains("雪")) {
            temperature = random.nextInt(10) - 10; // -10°C 到 0°C
        } else if (weatherType.contains("雨")) {
            temperature = random.nextInt(15) + 5; // 5°C 到 20°C
        } else if (weatherType.equals("晴")) {
            temperature = random.nextInt(15) + 15; // 15°C 到 30°C
        }
        
        // 创建天气信息对象并返回
        return WeatherInfo.builder()
                .city(city)
                .weather(weatherType)
                .temperature(temperature)
                .humidity(humidity)
                .windDirection(windDirection)
                .windSpeed(windSpeed)
                .updateTime(dateTime)
                .build();
    }
    
    /**
     * 模拟网络延迟
     * 随机延迟200-500毫秒
     */
    private void simulateDelay() {
        try {
            Thread.sleep(200 + random.nextInt(300));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("模拟延迟被中断", e);
        }
    }
}