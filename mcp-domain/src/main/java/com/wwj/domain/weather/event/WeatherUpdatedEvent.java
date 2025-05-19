package com.wwj.domain.weather.event;

import com.wwj.domain.core.event.DomainEvent;
import lombok.Getter;

/**
 * 天气更新事件
 *
 * @author wenjie
 * @since 1.0.0
 */
@Getter
public class WeatherUpdatedEvent extends DomainEvent {
    /**
     * 城市名称
     */
    private final String cityName;

    /**
     * 构造函数
     *
     * @param cityName 城市名称
     */
    public WeatherUpdatedEvent(String cityName) {
        super();
        this.cityName = cityName;
    }
}