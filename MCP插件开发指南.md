# MCP插件开发指南

## 1. 插件概述

MCP插件是扩展MCP平台功能的独立模块，遵循特定接口规范，可以被MCP平台动态加载和使用。插件机制是MCP平台实现功能扩展的核心方式。

### 1.1 插件特性

- **可插拔**：插件可以独立启用或禁用
- **松耦合**：插件与核心系统通过接口交互
- **可配置**：插件支持独立配置
- **热插拔**：支持运行时动态加载和卸载（高级特性）

### 1.2 插件类型

MCP支持以下几种插件类型：

- **功能插件**：提供特定业务功能
- **工具插件**：提供实用工具
- **集成插件**：与第三方系统集成
- **增强插件**：增强现有功能

## 2. 插件架构

### 2.1 插件模块结构

一个标准的MCP插件包含以下组件：

- **插件描述符**：定义插件元数据
- **插件接口**：定义插件功能
- **插件实现**：实现插件功能
- **插件配置**：插件配置属性
- **插件资源**：插件需要的资源文件

### 2.2 插件生命周期

插件的生命周期包括以下阶段：

1. **发现**：系统发现插件
2. **加载**：加载插件类和资源
3. **初始化**：初始化插件
4. **启动**：启动插件
5. **停止**：停止插件
6. **卸载**：卸载插件

## 3. 开发一个简单插件

### 3.1 创建插件项目

创建一个标准的Maven项目，添加以下依赖：

```xml
<dependency>
    <groupId>com.wwj</groupId>
    <artifactId>mcp-plugin-api</artifactId>
    <version>${mcp.version}</version>
    <scope>provided</scope>
</dependency>
```

### 3.2 定义插件接口

在`mcp-plugin-api`模块中定义插件接口：

```java
package com.wwj.plugin.api.example;

public interface ExamplePlugin {
    String processData(String input);
    void initialize();
    void shutdown();
}
```

### 3.3 实现插件

创建插件实现类：

```java
package com.example.plugin;

import com.wwj.plugin.api.annotation.MCP;
import com.wwj.plugin.api.example.ExamplePlugin;
import org.springframework.stereotype.Component;

@Component
@MCP(name = "example-plugin", version = "1.0.0")
public class ExamplePluginImpl implements ExamplePlugin {

    @Override
    public String processData(String input) {
        return "Processed: " + input;
    }

    @Override
    public void initialize() {
        // 初始化逻辑
    }

    @Override
    public void shutdown() {
        // 清理逻辑
    }
}
```

### 3.4 添加插件配置

创建配置类：

```java
package com.example.plugin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "mcp.plugin.example")
public class ExamplePluginProperties {
    private boolean enabled = true;
    private String apiKey;
    
    // Getter and Setter methods
}
```

### 3.5 添加自动配置

创建自动配置类：

```java
package com.example.plugin.config;

import com.example.plugin.ExamplePluginImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(name = "mcp.plugin.example.enabled", havingValue = "true", matchIfMissing = true)
public class ExamplePluginAutoConfiguration {

    @Bean
    public ExamplePluginImpl examplePlugin() {
        return new ExamplePluginImpl();
    }
}
```

### 3.6 配置META-INF

在`resources/META-INF/spring.factories`中添加：

```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
com.example.plugin.config.ExamplePluginAutoConfiguration
```

## 4. 高级插件开发

### 4.1 插件工具开发

MCP支持以工具形式开发插件，例如天气查询工具：

```java
package com.example.plugin.tool;

import com.wwj.plugin.api.annotation.Tool;
import org.springframework.stereotype.Component;

@Component
@Tool(name = "weather", description = "获取天气信息")
public class WeatherTool {

    @Tool.Action(name = "getWeather", description = "获取指定城市的天气")
    public WeatherResponse getWeather(String city) {
        // 实现获取天气的逻辑
        return new WeatherResponse();
    }
}
```

### 4.2 插件事件机制

插件可以使用事件机制进行通信：

```java
// 定义事件
public class WeatherUpdateEvent extends DomainEvent {
    private final String city;
    private final WeatherData weatherData;
    
    // Constructor, getters...
}

// 发布事件
@Autowired
private DomainEventPublisher eventPublisher;

public void updateWeather(String city, WeatherData data) {
    // 业务逻辑...
    eventPublisher.publish(new WeatherUpdateEvent(city, data));
}

// 订阅事件
@EventListener
public void handleWeatherUpdate(WeatherUpdateEvent event) {
    // 处理事件...
}
```

### 4.3 插件依赖管理

插件可以声明对其他插件的依赖：

```java
@MCP(
    name = "advanced-weather-plugin",
    version = "1.0.0",
    dependencies = {
        @MCPDependency(name = "basic-weather-plugin", version = ">=0.5.0"),
        @MCPDependency(name = "location-plugin", version = ">=1.0.0")
    }
)
public class AdvancedWeatherPluginImpl implements AdvancedWeatherPlugin {
    // 实现...
}
```

## 5. 插件最佳实践

### 5.1 插件设计原则

- **单一职责**：一个插件只做一件事
- **接口优先**：先定义接口，再实现功能
- **配置驱动**：支持通过配置调整行为
- **容错处理**：妥善处理异常情况
- **资源管理**：合理管理资源，避免泄漏

### 5.2 插件测试

创建专门的测试类：

```java
@SpringBootTest
@AutoConfigureMockMvc
public class ExamplePluginTests {

    @Autowired
    private ExamplePlugin examplePlugin;
    
    @Test
    public void testProcessData() {
        String result = examplePlugin.processData("test");
        assertEquals("Processed: test", result);
    }
}
```

### 5.3 插件文档

为插件提供完整的文档：

- 功能描述
- 配置选项
- 使用示例
- 依赖要求
- 常见问题

## 6. 插件部署与分发

### 6.1 打包插件

使用Maven打包插件：

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
            <configuration>
                <classifier>exec</classifier>
            </configuration>
        </plugin>
    </plugins>
</build>
```

### 6.2 发布插件

插件可以通过以下方式发布：

- Maven仓库
- Git仓库
- 自定义插件市场

### 6.3 安装插件

在主应用中添加插件依赖：

```xml
<dependency>
    <groupId>com.example</groupId>
    <artifactId>example-plugin</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 7. 插件常见问题

### 7.1 插件冲突

当多个插件提供相同功能时，可以使用`@Primary`注解指定优先级。

### 7.2 插件兼容性

使用版本约束确保插件兼容性：

```java
@MCP(name = "example-plugin", 
     version = "1.0.0", 
     mcpVersion = ">=2.0.0")
```

### 7.3 插件性能优化

- 避免插件初始化时进行重量级操作
- 使用懒加载模式加载资源
- 合理使用缓存提高性能

## 8. 示例插件

### 8.1 天气查询插件

提供天气查询功能：

```java
@MCP(name = "weather-plugin", version = "1.0.0")
public class WeatherPluginImpl implements WeatherPlugin {

    @Autowired
    private WeatherApiClient apiClient;
    
    @Override
    public WeatherData getWeather(String city) {
        return apiClient.queryWeather(city);
    }
}
```

### 8.2 文档管理插件

提供文档管理功能：

```java
@MCP(name = "doc-plugin", version = "1.0.0")
public class DocPluginImpl implements DocPlugin {

    @Autowired
    private DocRepository repository;
    
    @Override
    public void saveDoc(Document doc) {
        repository.save(doc);
    }
    
    @Override
    public Document getDoc(String id) {
        return repository.findById(id).orElse(null);
    }
}
```