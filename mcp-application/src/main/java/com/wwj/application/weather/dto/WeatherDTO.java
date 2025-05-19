package com.wwj.application.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 天气数据DTO
 *
 * @author wenjie
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherDTO {
    /**
     * 天气ID
     */
    private String id;

    /**
     * 城市名称
     */
    @NotBlank(message = "城市名称不能为空")
    private String cityName;

    /**
     * 温度
     */
    @NotNull(message = "温度不能为空")
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
}