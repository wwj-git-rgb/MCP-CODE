package com.wwj.infrastructure.weather.service;

import com.wwj.common.exception.BusinessException;
import com.wwj.domain.core.event.DomainEventPublisher;
import com.wwj.domain.weather.event.WeatherUpdatedEvent;
import com.wwj.domain.weather.model.Weather;
import com.wwj.domain.weather.repository.WeatherRepository;
import com.wwj.domain.weather.service.WeatherDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * 天气服务实现类
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Service
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherServiceImpl implements WeatherDomainService {

    private final Logger logger = LoggerFactory.getLogger(WeatherServiceImpl.class);

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private DomainEventPublisher eventPublisher;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${mcp.features.weather.api-url:https://api.example.com/weather}")
    private String weatherApiUrl;

    @Value("${mcp.features.weather.api-key:}")
    private String apiKey;

    @Override
    public Weather getWeatherByCity(String cityName) {
        if (!isValidCity(cityName)) {
            throw new BusinessException("无效的城市名称：" + cityName);
        }

        Weather weather = getWeatherFromCache(cityName);

        if (weather == null || weather.isExpired()) {
            logger.info("从外部API获取{}[{}]的天气信息", cityName, weather == null ? "缓存不存在" : "缓存过期");
            weather = getWeatherFromApi(cityName);
        } else {
            logger.info("从缓存获取{}的天气信息", cityName);
        }

        return weather;
    }

    @Override
    public Weather getWeatherByCityCode(String cityCode) {
        Optional<Weather> optionalWeather = weatherRepository.findByCityCode(cityCode);

        if (optionalWeather.isPresent() && !optionalWeather.get().isExpired()) {
            logger.info("从缓存获取城市编码{}的天气信息", cityCode);
            return optionalWeather.get();
        }

        logger.info("城市编码{}的天气信息不存在或已过期，将尝试从外部API获取", cityCode);
        
        // 这里简化处理，实际应该根据城市编码查询对应的城市名称，然后调用API
        throw new BusinessException("暂不支持根据城市编码直接从外部API获取天气信息");
    }

    @Override
    @Cacheable(value = "weatherCache", key = "#cityName", unless = "#result == null")
    public Weather getWeatherFromCache(String cityName) {
        logger.debug("尝试从数据库缓存中获取{}的天气信息", cityName);
        return weatherRepository.findByCityName(cityName).orElse(null);
    }

    @Override
    public Weather getWeatherFromApi(String cityName) {
        logger.info("从外部API获取{}的天气信息", cityName);
        
        try {
            // 模拟外部API调用，实际项目中应该实现实际的API调用
            // WebClient webClient = webClientBuilder.baseUrl(weatherApiUrl).build();
            // Weather weather = webClient.get()
            //     .uri(uriBuilder -> uriBuilder
            //         .queryParam("city", cityName)
            //         .queryParam("key", apiKey)
            //         .build())
            //     .retrieve()
            //     .bodyToMono(Weather.class)
            //     .block();
            
            // 模拟数据，实际项目应替换为真实API调用
            Weather weather = new Weather();
            weather.setId(UUID.randomUUID().toString());
            weather.setCityName(cityName);
            weather.setCityCode("101010100"); // 示例编码
            weather.setCondition("晴");
            weather.setTemperature(25.5);
            weather.setHumidity(45.0);
            weather.setWindDirection("东北");
            weather.setWindPower("三级");
            weather.setAqi(75);
            weather.setUpdateTime(LocalDateTime.now());
            weather.setSource("MCP模拟数据");
            weather.setDefaultExpireTime();
            
            logger.debug("从外部API获取到{}的天气信息: {}", cityName, weather);
            
            // 保存到数据库
            weather = weatherRepository.save(weather);
            
            // 发布事件
            eventPublisher.publishAsync(new WeatherUpdatedEvent(cityName, weather));
            
            return weather;
        } catch (Exception e) {
            logger.error("从外部API获取{}的天气信息失败: {}", cityName, e.getMessage());
            throw new BusinessException("获取天气信息失败: " + e.getMessage());
        }
    }

    @Override
    @CacheEvict(value = "weatherCache", key = "#weather.cityName")
    public Weather updateWeather(Weather weather) {
        logger.info("更新{}的天气信息", weather.getCityName());
        
        // 更新时间
        weather.setUpdateTime(LocalDateTime.now());
        
        // 重新设置过期时间
        weather.setDefaultExpireTime();
        
        // 保存到数据库
        Weather savedWeather = weatherRepository.save(weather);
        
        // 发布事件
        eventPublisher.publishAsync(new WeatherUpdatedEvent(weather.getCityName(), savedWeather));
        
        return savedWeather;
    }

    @Override
    public boolean isValidCity(String cityName) {
        // 实际应用中应根据城市列表进行验证
        // 这里简化处理，任何非空城市名都视为有效
        return cityName != null && !cityName.trim().isEmpty();
    }
}
