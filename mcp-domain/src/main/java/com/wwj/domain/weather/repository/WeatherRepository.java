package com.wwj.domain.weather.repository;

import com.wwj.domain.core.repository.Repository;
import com.wwj.domain.weather.model.Weather;

import java.util.Optional;

/**
 * 天气仓储接口
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public interface WeatherRepository extends Repository<Weather, String> {

    /**
     * 根据城市名称查询天气信息
     *
     * @param cityName 城市名称
     * @return 天气信息（可能为空）
     */
    Optional<Weather> findByCityName(String cityName);

    /**
     * 根据城市编码查询天气信息
     *
     * @param cityCode 城市编码
     * @return 天气信息（可能为空）
     */
    Optional<Weather> findByCityCode(String cityCode);

    /**
     * 删除指定城市的天气信息
     *
     * @param cityName 城市名称
     */
    void deleteByCityName(String cityName);

    /**
     * 删除指定城市编码的天气信息
     *
     * @param cityCode 城市编码
     */
    void deleteByCityCode(String cityCode);
}
