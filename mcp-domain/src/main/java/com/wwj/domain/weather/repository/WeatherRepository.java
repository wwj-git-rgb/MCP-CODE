package com.wwj.domain.weather.repository;

import com.wwj.domain.core.repository.Repository;
import com.wwj.domain.weather.model.Weather;

import java.util.Optional;

/**
 * 天气仓储接口
 *
 * @author wenjie
 * @since 1.0.0
 */
public interface WeatherRepository extends Repository<Weather, String> {
    /**
     * 根据城市名称查询天气
     *
     * @param cityName 城市名称
     * @return 天气信息可选项
     */
    Optional<Weather> findByCityName(String cityName);
}