package com.wwj.application.weather.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * 天气信息数据传输对象
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Data
public class WeatherDTO implements Serializable {

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
     * 天气状况
     */
    private String condition;

    /**
     * 温度（摄氏度）
     */
    private Double temperature;

    /**
     * 湿度（百分比）
     */
    private Double humidity;

    /**
     * 风向
     */
    private String windDirection;

    /**
     * 风力
     */
    private String windPower;

    /**
     * 空气质量指数
     */
    private Integer aqi;

    /**
     * 更新时间
     */
    private String updateTime;

    /**
     * 数据来源
     */
    private String source;
}
