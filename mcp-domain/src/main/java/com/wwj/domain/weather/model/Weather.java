package com.wwj.domain.weather.model;

import com.wwj.domain.core.annotation.AggregateRoot;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 天气数据模型
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@AggregateRoot
public class Weather {
    /**
     * 天气ID
     */
    private String id;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 温度
     */
    private Double temperature;

    /**
     * 湿度
     */
    private Double humidity;

    /**
     * 天气状况
     */
    private String weatherCondition;

    /**
     * 风速
     */
    private Double windSpeed;

    /**
     * 风向
     */
    private String windDirection;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 是否过期
     *
     * @return 是否过期
     */
    public boolean isExpired() {
        // 默认时间超过1小时即过期
        return updateTime.plusHours(1).isBefore(LocalDateTime.now());
    }
}