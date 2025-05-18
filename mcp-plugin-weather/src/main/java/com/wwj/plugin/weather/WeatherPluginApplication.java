package com.wwj.plugin.weather;

import com.wwj.plugin.weather.model.WeatherInfo;
import com.wwj.plugin.weather.service.WeatherService;
import com.wwj.plugin.weather.tool.WeatherMcpTool;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * 天气插件应用启动类
 * 用于测试和演示天气插件功能
 */
@SpringBootApplication
public class WeatherPluginApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherPluginApplication.class, args);
    }
    
    /**
     * 启动后运行测试代码
     */
    @Bean
    public CommandLineRunner commandLineRunner(WeatherMcpTool weatherTool) {
        return args -> {
            System.out.println("=== 天气插件测试 ===");
            
            // 测试获取当前天气
            System.out.println("\n获取当前天气:");
            WeatherInfo currentWeather = weatherTool.getCurrentWeather("北京");
            printWeatherInfo(currentWeather);
            
            // 测试获取天气预报
            System.out.println("\n获取天气预报:");
            List<WeatherInfo> forecast = weatherTool.getWeatherForecast("上海", 3);
            for (WeatherInfo weather : forecast) {
                printWeatherInfo(weather);
                System.out.println("--------------");
            }
            
            System.out.println("\n测试完成，按Ctrl+C退出程序");
        };
    }
    
    /**
     * 打印天气信息
     */
    private static void printWeatherInfo(WeatherInfo weatherInfo) {
        System.out.println("城市: " + weatherInfo.getCity());
        System.out.println("天气: " + weatherInfo.getWeather());
        System.out.println("温度: " + weatherInfo.getTemperature() + "°C");
        System.out.println("湿度: " + weatherInfo.getHumidity() + "%");
        System.out.println("风向: " + weatherInfo.getWindDirection());
        System.out.println("风速: " + weatherInfo.getWindSpeed() + " km/h");
        System.out.println("更新时间: " + weatherInfo.getUpdateTime());
    }
}