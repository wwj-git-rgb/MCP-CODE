package com.wwj.domain.weather.event;

import com.wwj.domain.core.event.DomainEvent;
import com.wwj.domain.weather.model.Weather;

/**
 * 天气信息更新事件
 *
 * @author wenjie wang
 * @since 1.0.0
 */
public class WeatherUpdatedEvent extends DomainEvent {

    private static final long serialVersionUID = 1L;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 城市编码
     */
    private String cityCode;

    /**
     * 天气信息
     */
    private Weather weather;

    /**
     * 构造函数
     *
     * @param cityName 城市名称
     * @param weather  天气信息
     */
    public WeatherUpdatedEvent(String cityName, Weather weather) {
        super();
        this.cityName = cityName;
        this.cityCode = weather.getCityCode();
        this.weather = weather;
        this.setSource("weather-service");
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }
}
