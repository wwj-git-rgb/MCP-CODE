# MCP多云平台

MCP (Multi-Cloud Platform) 是一个基于插件化架构的多云服务平台，采用领域驱动设计(DDD)架构，提供统一的API接口和强大的服务扩展能力。

## 项目概述

MCP平台设计目标是构建一个灵活、可扩展的云服务集成平台，通过插件化架构实现功能的动态扩展，使开发者能够快速集成和管理多种云服务。

### 主要特性

- **插件化架构**：动态加载、管理插件，支持功能的即插即用
- **领域驱动设计**：严格遵循DDD原则，实现业务逻辑与技术实现分离 
- **统一API接口**：提供标准化的服务调用接口，简化集成难度
- **多云服务支持**：通过插件机制支持多种云服务提供商
- **可插拔组件**：所有功能组件均可独立启用/禁用
- **事件驱动机制**：采用领域事件实现模块间解耦通信

## 系统架构

MCP采用分层架构和插件化设计，主要包括以下模块：

```
MCP-CODE
├── mcp-common       # 通用模块，定义常量、异常和结果封装
├── mcp-domain       # 领域层，包含核心业务模型和领域服务接口
├── mcp-application  # 应用层，封装业务逻辑和协调多个服务
├── mcp-infrastructure # 基础设施层，提供领域服务实现
├── mcp-plugin-api   # 插件API，定义扩展点接口
├── mcp-plugin-core  # 插件核心，实现插件加载和管理
├── mcp-plugin-weather # 天气服务插件示例
├── mcp-api          # API层，提供MCP工具实现
├── mcp-web          # Web层，处理HTTP请求和异常
└── mcp-starter      # 启动器，集成各个组件
```

### 架构分层关系

```
                   +----------------+
                   |                |
                   | mcp-web (表现层) |
                   |                |
                   +-------+--------+
                           |
                           | 依赖接口
                           ▼
+----------------+  +------+-------+  +------------------+
|                |  |              |  |                  |
| mcp-api (工具实现) | | mcp-starter | | mcp-application  |
|                |  | (自动配置)    |  | (应用服务)        |
+-------+--------+  +------+-------+  +--------+---------+
        |                  |                   |
        +------------------+-------------------+
                           |
                           | 依赖接口
                           ▼
                  +--------+---------+
                  |                  |
                  | mcp-infrastructure |
                  | (基础设施)         |
                  +--------+---------+
                           |
                           | 依赖接口
                           ▼
                  +--------+---------+
                  |                  |
                  | mcp-domain (领域层) |
                  |                  |
                  +--------+---------+
                           |
                           | 依赖
                           ▼
                  +--------+---------+
                  |                  |
                  | mcp-common (公共层) |
                  |                  |
                  +------------------+
```

## 领域驱动设计实现

本项目严格遵循领域驱动设计(DDD)架构，划分为以下几层：

### 领域层 (mcp-domain)

核心业务逻辑和规则所在的层，包含：

- **实体(Entity)**: 具有唯一标识的对象，如 `WeatherReport`
- **值对象(Value Object)**: 无需唯一标识的对象，如 `Location`
- **聚合(Aggregate)**: 业务完整性单元，如以 `WeatherReport` 为聚合根
- **领域事件(Domain Event)**: 领域中发生的重要事件，如 `WeatherUpdatedEvent`
- **领域服务(Domain Service)**: 不属于单个实体的业务逻辑，如 `WeatherDomainService`
- **仓储接口(Repository)**: 持久化抽象，如 `WeatherReportRepository`

### 应用层 (mcp-application)

协调领域对象完成业务用例的层，包含：

- **应用服务(Application Service)**: 用例实现，如 `WeatherApplicationService`
- **DTO (Data Transfer Object)**: 数据传输对象，如 `WeatherInfoDTO`、`WeatherReportDTO`

### 基础设施层 (mcp-infrastructure)

提供技术实现的层，包含：

- **仓储实现**: 实现领域层定义的仓储接口
- **外部服务集成**: 如天气API调用、消息队列等

### 接口层 (mcp-api, mcp-web)

与外部交互的层，包含：

- **控制器(Controller)**: REST API等接口
- **适配器(Adapter)**: 转换外部数据为应用可用格式

## 插件化架构

MCP平台的插件化架构由以下几个部分组成：

1. **扩展点(Extension Point)**: 在`mcp-plugin-api`模块中定义，使用`@McpExtensionPoint`注解标记
2. **扩展实现(Extension)**: 由插件提供，使用`@McpExtension`注解标记
3. **插件管理(Plugin Management)**: 在`mcp-plugin-core`模块中实现，负责插件的加载和管理

### 插件系统设计原则

- **开闭原则**：平台核心功能对扩展开放，对修改关闭
- **依赖倒置**：插件与平台间通过接口通信，降低耦合
- **功能内聚**：每个插件专注于特定功能领域
- **组件自治**：插件内部实现细节对平台透明

## 快速开始

### 环境要求

- JDK 11+
- Maven 3.6+
- Spring Boot 2.7+

### 构建与运行

```bash
# 编译项目
mvn clean install

# 运行应用
java -jar mcp-starter/target/mcp-starter-1.0-SNAPSHOT.jar
```

### 配置说明

主要配置项:

```yaml
# 服务器配置
server:
  port: 8080

# MCP服务配置
mcp:
  service:
    enabled: true
  plugin:
    enabled: true
    plugin-path: plugins
    scan-packages:
      - com.wwj.plugin
    plugins:
      com.wwj.weather: true

# 功能模块配置
features:
  weather:
    enabled: true
  documentation:
    enabled: true
```

## 示例: 天气服务

MCP提供了天气服务插件示例，展示了基于DDD和插件架构的功能实现:

```java
// 领域服务接口
public interface WeatherDomainService {
    WeatherReport createWeatherReport(Location location, WeatherInfo currentWeather, List<WeatherInfo> forecasts);
    WeatherReport updateCurrentWeather(String reportId, WeatherInfo newWeatherInfo);
    Optional<WeatherReport> getWeatherReportByLocation(double longitude, double latitude);
}

// 应用服务实现
@Service
public class WeatherApplicationServiceImpl implements WeatherApplicationService {
    private final WeatherDomainService weatherDomainService;
    
    // 应用服务编排领域服务，实现业务用例
    @Override
    public WeatherReportDTO getWeatherReport(double longitude, double latitude) {
        Optional<WeatherReport> reportOpt = weatherDomainService.getWeatherReportByLocation(longitude, latitude);
        return reportOpt.map(this::convertToDTO).orElse(null);
    }
}

// 插件实现
@Service
@McpExtension(name = "aMapWeatherService", description = "高德地图天气服务", priority = 10)
public class AMapWeatherService implements WeatherServiceExtensionPoint {
    // 实现方法...
}
```

## 文档索引

详细开发指南和规范请参考：

- [MCP开发手册](MCP开发手册.md) - 综合开发指南
- [MCP插件开发指南](MCP插件开发指南.md) - 插件开发详细说明
- [开发规范](开发规范.md) - 编码和DDD设计规范

## 贡献指南

1. Fork 本仓库
2. 创建功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交更改 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建Pull Request

## 许可证

Copyright (c) 2023 WWJ