package com.wwj.plugin.weather.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 天气信息实体类
 * 用于存储和传输天气数据
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfo {
    
    /**
     * 城市名称
     */
    private String city;
    
    /**
     * 天气状况，例如：晴、多云、雨等
     */
    private String weather;
    
    /**
     * 温度，单位：摄氏度
     */
    private Integer temperature;
    
    /**
     * 湿度，单位：百分比
     */
    private Integer humidity;
    
    /**
     * 风向，例如：东风、西北风等
     */
    private String windDirection;
    
    /**
     * 风速，单位：km/h
     */
    private Integer windSpeed;
    
    /**
     * 数据更新时间
     */
    private LocalDateTime updateTime;
}