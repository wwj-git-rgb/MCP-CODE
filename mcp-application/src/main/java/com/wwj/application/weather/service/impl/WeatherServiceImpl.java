package com.wwj.application.weather.service.impl;

import com.wwj.application.weather.dto.WeatherDTO;
import com.wwj.application.weather.service.WeatherService;
import com.wwj.domain.weather.model.Weather;
import com.wwj.domain.weather.service.WeatherDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

/**
 * 天气应用服务实现
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Service
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherServiceImpl implements WeatherService {

    private final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    private WeatherDomainService weatherDomainService;

    @Override
    public WeatherDTO getWeatherByCity(String cityName) {
        logger.info("获取{}的天气信息", cityName);
        Weather weather = weatherDomainService.getWeatherByCity(cityName);
        return convertToDTO(weather);
    }

    @Override
    public WeatherDTO getWeatherByCityCode(String cityCode) {
        logger.info("获取城市编码{}的天气信息", cityCode);
        Weather weather = weatherDomainService.getWeatherByCityCode(cityCode);
        return convertToDTO(weather);
    }

    @Override
    public WeatherDTO refreshWeather(String cityName) {
        logger.info("强制刷新{}的天气信息", cityName);
        Weather weather = weatherDomainService.getWeatherFromApi(cityName);
        return convertToDTO(weather);
    }

    /**
     * 将领域模型转换为DTO
     *
     * @param weather 天气领域模型
     * @return 天气DTO
     */
    private WeatherDTO convertToDTO(Weather weather) {
        if (weather == null) {
            return null;
        }

        WeatherDTO dto = new WeatherDTO();
        dto.setCityName(weather.getCityName());
        dto.setCityCode(weather.getCityCode());
        dto.setCondition(weather.getCondition());
        dto.setTemperature(weather.getTemperature());
        dto.setHumidity(weather.getHumidity());
        dto.setWindDirection(weather.getWindDirection());
        dto.setWindPower(weather.getWindPower());
        dto.setAqi(weather.getAqi());
        dto.setSource(weather.getSource());

        // 格式化时间
        if (weather.getUpdateTime() != null) {
            dto.setUpdateTime(weather.getUpdateTime().format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }

        return dto;
    }
}
