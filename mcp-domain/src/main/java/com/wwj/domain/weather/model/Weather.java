package com.wwj.domain.weather.model;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 天气信息领域模型
 *
 * @author wenjie wang
 * @since 1.0.0
 */
@Data
public class Weather implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 天气ID
     */
    private String id;

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
     * 数据更新时间
     */
    private LocalDateTime updateTime;

    /**
     * 数据过期时间（默认30分钟）
     */
    private LocalDateTime expireTime;

    /**
     * 数据来源
     */
    private String source;

    /**
     * 判断数据是否过期
     *
     * @return 是否过期
     */
    public boolean isExpired() {
        return expireTime != null && LocalDateTime.now().isAfter(expireTime);
    }

    /**
     * 设置过期时间（默认30分钟）
     */
    public void setDefaultExpireTime() {
        this.expireTime = LocalDateTime.now().plusMinutes(30);
    }
}
