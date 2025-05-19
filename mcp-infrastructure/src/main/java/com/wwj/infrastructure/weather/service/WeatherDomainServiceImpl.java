package com.wwj.infrastructure.weather.service;

import com.wwj.common.exception.BusinessException;
import com.wwj.domain.core.event.DomainEventPublisher;
import com.wwj.domain.weather.event.WeatherUpdatedEvent;
import com.wwj.domain.weather.model.Weather;
import com.wwj.domain.weather.repository.WeatherRepository;
import com.wwj.domain.weather.service.WeatherDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * 天气领域服务实现
 *
 * @author wenjie
 * @since 1.0.0
 */
@Slf4j
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "weather")
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherDomainServiceImpl implements WeatherDomainService {

    private final WeatherRepository weatherRepository;
    private final DomainEventPublisher eventPublisher;
    private final WeatherApiClient weatherApiClient;

    @Override
    @Cacheable(key = "#cityName")
    public Weather getWeatherByCityName(String cityName) {
        log.info("获取城市[{}]的天气数据", cityName);
        
        // 从仓储中查询
        Optional<Weather> weatherOptional = weatherRepository.findByCityName(cityName);
        
        // 如果数据库中存在且未过期，直接返回
        if (weatherOptional.isPresent() && !weatherOptional.get().isExpired()) {
            return weatherOptional.get();
        }
        
        // 从天气API获取最新数据
        try {
            Weather weather = weatherApiClient.getWeatherForCity(cityName);
            weather.setId(UUID.randomUUID().toString());
            weather.setUpdateTime(LocalDateTime.now());
            
            // 保存到仓储
            Weather savedWeather = weatherRepository.save(weather);
            
            // 发布天气更新事件
            eventPublisher.publish(new WeatherUpdatedEvent(cityName));
            
            return savedWeather;
        } catch (Exception e) {
            log.error("获取城市[{}]的天气数据失败", cityName, e);
            throw new BusinessException("获取天气数据失败：" + e.getMessage());
        }
    }

    @Override
    @CacheEvict(key = "#weather.cityName")
    public Weather updateWeather(Weather weather) {
        log.info("更新城市[{}]的天气数据", weather.getCityName());
        
        // 检查是否存在
        Optional<Weather> existingWeather = weatherRepository.findByCityName(weather.getCityName());
        
        // 如果存在，更新ID和更新时间
        if (existingWeather.isPresent()) {
            weather.setId(existingWeather.get().getId());
        } else {
            weather.setId(UUID.randomUUID().toString());
        }
        
        // 设置更新时间
        weather.setUpdateTime(LocalDateTime.now());
        
        // 保存到仓储
        Weather savedWeather = weatherRepository.save(weather);
        
        // 发布天气更新事件
        eventPublisher.publish(new WeatherUpdatedEvent(weather.getCityName()));
        
        return savedWeather;
    }
}