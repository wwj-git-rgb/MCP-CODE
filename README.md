# MCP-CODE 天气服务插件

MCP-CODE是一个基于Spring Boot的MCP（模型上下文协议）插件，提供天气服务功能，包括当前天气查询和天气预报。

## 项目结构

该项目采用多模块Maven架构，主要包含以下模块：

- **mcp-api**：API接口定义
- **mcp-application**：应用层实现
- **mcp-common**：公共组件和工具
- **mcp-domain**：领域模型和服务
- **mcp-infrastructure**：基础设施实现
- **mcp-plugin-api**：插件API定义
- **mcp-plugin-core**：插件核心实现
- **mcp-plugin-weather**：天气服务插件实现
- **mcp-starter**：应用启动器
- **mcp-web**：Web层实现

## 天气插件核心功能

### 1. 模型定义

- `WeatherInfo`：天气信息实体类，包含城市、天气状况、温度、湿度、风向、风速等信息

### 2. 服务接口

- `WeatherService`：定义了获取当前天气和天气预报的方法
- `WeatherServiceImpl`：服务接口的模拟实现，生成随机天气数据

### 3. MCP工具实现

- `WeatherMcpTool`：提供天气查询MCP工具方法，支持当前天气查询和天气预报

### 4. REST API接口

- `WeatherController`：提供HTTP REST接口，可通过浏览器或API客户端调用

## 如何使用

### 1. 启动服务

```bash
java -jar mcp-plugin-weather/target/mcp-plugin-weather-0.0.1-SNAPSHOT.jar
```

服务将在8081端口启动，并使用`/mcp-weather`作为上下文路径。

### 2. API接口

获取当前天气：
```
GET /mcp-weather/api/weather/current/{城市名}
```

获取天气预报：
```
GET /mcp-weather/api/weather/forecast/{城市名}/{天数}
```

### 3. 作为MCP工具使用

该插件可以作为MCP工具在MCP系统中调用，支持：

- 当前天气查询：根据城市名称查询当前天气情况
- 天气预报查询：根据城市名称和预报天数查询未来天气预报

## 技术特点

1. **模块化设计**：遵循DDD (领域驱动设计) 思想，将系统拆分为多个模块
2. **插件化架构**：基于MCP插件系统实现，可动态加载和卸载
3. **RestAPI接口**：提供标准RESTful API，便于集成
4. **完善的日志**：使用SLF4J + Lombok实现全面的日志跟踪
5. **丰富的注释**：中文注释说明，便于理解和维护

## 实现说明

当前天气插件的实现是基于模拟数据，没有对接真实的天气API。在实际应用中，可以替换`WeatherServiceImpl`的实现，对接第三方天气API服务，如和风天气、高德天气等。

## 环境要求

- JDK 17+
- Maven 3.6+
- Spring Boot 3.2.0

## 联系方式

MCP开发团队