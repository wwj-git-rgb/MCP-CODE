package com.wwj.plugin.weather.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wwj.application.weather.dto.WeatherDTO;
import com.wwj.application.weather.service.WeatherService;
import com.wwj.plugin.api.annotation.MCP;
import com.wwj.plugin.api.weather.WeatherPlugin;
import com.wwj.plugin.weather.model.WeatherPluginProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * 天气插件实现
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Service
@MCP(name = "weather-plugin", version = "1.0.0", description = "提供天气查询功能", author = "MCP Team")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.plugins.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherPluginImpl implements WeatherPlugin {

    private final WeatherService weatherService;
    private final WeatherPluginProperties properties;
    private final ObjectMapper objectMapper;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public String getWeather(String city) {
        log.info("插件获取城市[{}]天气信息", city);
        
        try {
            WeatherDTO weatherDTO = weatherService.getWeatherByCity(city);
            
            // 构建响应数据
            StringBuilder result = new StringBuilder();
            result.append("城市: ").append(weatherDTO.getCityName()).append("\n");
            result.append("天气状况: ").append(weatherDTO.getWeatherCondition()).append("\n");
            result.append("温度: ").append(String.format("%.1f°C", weatherDTO.getTemperature())).append("\n");
            result.append("湿度: ").append(String.format("%.1f%%", weatherDTO.getHumidity())).append("\n");
            result.append("风向: ").append(weatherDTO.getWindDirection()).append("\n");
            result.append("风速: ").append(String.format("%.1f m/s", weatherDTO.getWindSpeed())).append("\n");
            result.append("更新时间: ").append(weatherDTO.getUpdateTime().format(dateFormatter));
            
            return result.toString();
        } catch (Exception e) {
            log.error("获取天气信息失败", e);
            return "获取天气信息失败: " + e.getMessage();
        }
    }

    @Override
    public String getForecast(String city, int days) {
        log.info("插件获取城市[{}]未来{}\u5929天气预报", city, days);
        
        if (days < 1 || days > 7) {
            return "预报天数应在 1-7 之间";
        }
        
        // 注意：这里模拟预报数据，实际应从天气API获取
        StringBuilder result = new StringBuilder();
        result.append("城市: ").append(city).append(" 未来").append(days).append("天预报\n\n");
        
        for (int i = 0; i < days; i++) {
            result.append("第").append(i + 1).append("天: \n");
            result.append("  日期: 2023-").append(String.format("%02d-%02d", 12, 1 + i)).append("\n");
            result.append("  天气: ").append(getRandomWeatherCondition()).append("\n");
            result.append("  温度: ").append(String.format("%.1f°C", 10 + Math.random() * 15)).append("\n");
            result.append("  湿度: ").append(String.format("%.1f%%", 50 + Math.random() * 30)).append("\n");
            result.append("\n");
        }
        
        return result.toString();
    }
    
    private String getRandomWeatherCondition() {
        String[] conditions = {
                "晴天", "多云", "阴天", "小雨", "中雨",
                "大雨", "雪", "雾", "阴转晴", "多云转小雨"
        };
        return conditions[(int) (Math.random() * conditions.length)];
    }
}