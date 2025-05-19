package com.wwj.web.controller;

import com.wwj.application.weather.dto.WeatherDTO;
import com.wwj.application.weather.service.WeatherService;
import com.wwj.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * 天气控制器
 *
 * @author wenjie
 * @since 1.0.0
 */
@Tag(name = "天气控制器", description = "提供天气查询相关接口")
@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "mcp.features.weather", name = "enabled", havingValue = "true", matchIfMissing = true)
public class WeatherController {

    private final WeatherService weatherService;

    @Operation(summary = "查询城市天气", description = "根据城市名称查询天气数据")
    @GetMapping("/{city}")
    public Result<WeatherDTO> getWeather(
            @Parameter(description = "城市名称", example = "北京", required = true)
            @PathVariable String city) {
        WeatherDTO weatherDTO = weatherService.getWeatherByCity(city);
        return Result.success(weatherDTO);
    }

    @Operation(summary = "更新天气数据", description = "更新指定城市的天气数据")
    @PostMapping
    public Result<WeatherDTO> updateWeather(@Validated @RequestBody WeatherDTO weatherDTO) {
        WeatherDTO updatedWeatherDTO = weatherService.updateWeather(weatherDTO);
        return Result.success(updatedWeatherDTO);
    }
}