package com.wwj.plugin.weather.controller;

import com.wwj.common.result.Result;
import com.wwj.plugin.api.weather.WeatherPlugin;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.*;

/**
 * 天气插件控制器
 *
 * @author wenjie
 * @since 1.0.0
 */
@Tag(name = "天气插件控制器", description = "提供天气插件的API接口")
@RestController
@RequestMapping("/api/plugins/weather")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.plugins.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherPluginController {

    private final WeatherPlugin weatherPlugin;

    @Operation(summary = "获取城市天气", description = "获取指定城市的天气信息")
    @GetMapping("/{city}")
    public Result<String> getWeather(
            @Parameter(description = "城市名称", example = "北京", required = true)
            @PathVariable String city) {
        String weather = weatherPlugin.getWeather(city);
        return Result.success(weather);
    }

    @Operation(summary = "获取天气预报", description = "获取指定城市的天气预报")
    @GetMapping("/{city}/forecast")
    public Result<String> getForecast(
            @Parameter(description = "城市名称", example = "北京", required = true)
            @PathVariable String city,
            
            @Parameter(description = "预报天数", example = "3", required = true)
            @RequestParam(defaultValue = "3") int days) {
        String forecast = weatherPlugin.getForecast(city, days);
        return Result.success(forecast);
    }
}