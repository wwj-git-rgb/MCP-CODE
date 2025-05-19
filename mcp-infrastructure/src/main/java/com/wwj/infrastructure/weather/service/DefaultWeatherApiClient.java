package com.wwj.infrastructure.weather.service;

import com.wwj.domain.weather.model.Weather;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * 默认天气API客户端实现
 * 注意：这是一个模拟实现，实际项目中应连接真实API
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Component
@ConditionalOnProperty(
        prefix = "mcp.features.weather",
        name = "provider",
        havingValue = "default",
        matchIfMissing = true
)
public class DefaultWeatherApiClient implements WeatherApiClient {

    @Value("${mcp.features.weather.api-key:demo-api-key}")
    private String apiKey;

    private final Random random = new Random();

    @Override
    public Weather getWeatherForCity(String cityName) {
        log.info("使用默认天气API客户端获取城市[{}]的天气数据", cityName);
        
        // 模拟网络请求延迟
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // 创建模拟天气数据
        Weather weather = new Weather();
        weather.setCityName(cityName);
        weather.setTemperature(getRandomTemperature());
        weather.setHumidity(getRandomHumidity());
        weather.setWeatherCondition(getRandomWeatherCondition());
        weather.setWindSpeed(getRandomWindSpeed());
        weather.setWindDirection(getRandomWindDirection());
        weather.setUpdateTime(LocalDateTime.now());
        
        return weather;
    }
    
    // 生成模拟数据的辅助方法
    
    private double getRandomTemperature() {
        // 生成-10到35度之间的随机温度
        return -10 + random.nextDouble() * 45;
    }
    
    private double getRandomHumidity() {
        // 生成30%-100%之间的随机湿度
        return 30 + random.nextDouble() * 70;
    }
    
    private String getRandomWeatherCondition() {
        String[] conditions = {
                "晴天", "多云", "阴天", "小雨", "中雨",
                "大雨", "暴雨", "雪", "雾", "沙尘"
        };
        return conditions[random.nextInt(conditions.length)];
    }
    
    private double getRandomWindSpeed() {
        // 生成0-30 m/s之间的随机风速
        return random.nextDouble() * 30;
    }
    
    private String getRandomWindDirection() {
        String[] directions = {
                "东风", "东南风", "南风", "西南风",
                "西风", "西北风", "北风", "东北风"
        };
        return directions[random.nextInt(directions.length)];
    }
}