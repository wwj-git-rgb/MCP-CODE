package com.wwj.application.weather.service.impl;

import com.wwj.application.weather.dto.WeatherDTO;
import com.wwj.application.weather.service.WeatherService;
import com.wwj.domain.weather.model.Weather;
import com.wwj.domain.weather.service.WeatherDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * 天气应用服务实现
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherServiceImpl implements WeatherService {

    private final WeatherDomainService weatherDomainService;

    @Override
    public WeatherDTO getWeatherByCity(String cityName) {
        log.info("获取城市[{}]的天气数据", cityName);
        
        // 调用领域服务
        Weather weather = weatherDomainService.getWeatherByCityName(cityName);
        
        // 转换为DTO
        return convertToDTO(weather);
    }

    @Override
    public WeatherDTO updateWeather(WeatherDTO weatherDTO) {
        log.info("更新城市[{}]的天气数据", weatherDTO.getCityName());
        
        // 转换为领域模型
        Weather weather = convertToDomain(weatherDTO);
        
        // 调用领域服务更新
        Weather updatedWeather = weatherDomainService.updateWeather(weather);
        
        // 转换为DTO并返回
        return convertToDTO(updatedWeather);
    }
    
    /**
     * 将领域模型转换为DTO
     * 
     * @param weather 天气领域模型
     * @return 天气DTO
     */
    private WeatherDTO convertToDTO(Weather weather) {
        return WeatherDTO.builder()
                .id(weather.getId())
                .cityName(weather.getCityName())
                .temperature(weather.getTemperature())
                .humidity(weather.getHumidity())
                .weatherCondition(weather.getWeatherCondition())
                .windSpeed(weather.getWindSpeed())
                .windDirection(weather.getWindDirection())
                .updateTime(weather.getUpdateTime())
                .build();
    }
    
    /**
     * 将DTO转换为领域模型
     * 
     * @param dto 天气DTO
     * @return 天气领域模型
     */
    private Weather convertToDomain(WeatherDTO dto) {
        Weather weather = new Weather();
        weather.setId(dto.getId());
        weather.setCityName(dto.getCityName());
        weather.setTemperature(dto.getTemperature());
        weather.setHumidity(dto.getHumidity());
        weather.setWeatherCondition(dto.getWeatherCondition());
        weather.setWindSpeed(dto.getWindSpeed());
        weather.setWindDirection(dto.getWindDirection());
        weather.setUpdateTime(dto.getUpdateTime());
        return weather;
    }
}