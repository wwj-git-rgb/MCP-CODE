# MCP - Modular Configuration Platform

MCP是一个基于Spring Boot的模块化配置平台，旨在提供灵活、可扩展的微服务架构解决方案。

## 项目概述

MCP平台采用领域驱动设计（DDD）思想，通过插件化架构实现功能的可插拔性，支持各种业务场景的动态扩展和配置。

### 核心特性

- **模块化设计**：基于DDD和插件化架构
- **可插拔功能**：支持功能模块的动态启用和禁用
- **统一配置**：集中管理各模块配置项
- **接口与实现分离**：遵循依赖倒置原则
- **丰富的扩展点**：支持自定义功能扩展

## 技术栈

- Java 11+
- Spring Boot 2.7+
- Maven 3.6+
- Spring Data JPA
- Spring Cloud (可选)

## 项目结构

MCP项目由以下模块组成：

```
mcp-common        - 公共组件和工具类
mcp-domain        - 领域模型和服务接口
mcp-infrastructure - 基础设施实现
mcp-application   - 应用服务
mcp-api           - API和工具实现
mcp-starter       - 自动配置和集成
mcp-web           - Web控制器和接口
mcp-plugin-xxx    - 各种功能插件
```

## 快速开始

### 前置条件

- JDK 11 或更高版本
- Maven 3.6 或更高版本
- IDE (推荐 IntelliJ IDEA)

### 编译与构建

1. 克隆项目

```bash
git clone https://github.com/yourusername/MCP.git
cd MCP
```

2. 编译项目

```bash
mvn clean install -DskipTests
```

3. 运行应用

```bash
java -jar mcp-service/target/mcp-service-1.0.0-SNAPSHOT.jar
```

### 基本配置

创建`application.yml`配置文件：

```yaml
mcp:
  enabled: true
  features:
    weather:
      enabled: true
      api-key: YOUR_API_KEY
    documentation:
      enabled: true
```

## 核心模块说明

### 公共层 (mcp-common)

提供全局通用功能，如异常、常量、结果封装等。

### 领域层 (mcp-domain)

定义领域模型和服务接口，是业务核心。

### 基础设施层 (mcp-infrastructure)

实现领域服务接口，提供具体的技术实现。

### 应用层 (mcp-application)

编排业务流程，实现应用服务，处理DTO转换。

### API层 (mcp-api)

实现MCP工具接口，提供具体的工具实现。

### 启动层 (mcp-starter)

提供自动配置，集成所有模块，实现条件装配。

### Web层 (mcp-web)

提供Web接口和控制器，处理HTTP请求。

## 功能模块

### 天气服务模块

提供天气查询功能，支持多种数据源。

```java
@Autowired
private WeatherService weatherService;

// 获取天气信息
WeatherData data = weatherService.getWeatherByCity("北京");
```

### 文档管理模块

提供文档存储和检索功能。

```java
@Autowired
private DocumentService documentService;

// 保存文档
documentService.saveDocument(new Document("title", "content"));

// 获取文档
Document doc = documentService.getDocumentById("doc123");
```

## 插件开发

MCP支持通过插件机制扩展功能。详见[MCP插件开发指南](MCP插件开发指南.md)。

简单示例：

```java
@MCP(name = "example-plugin", version = "1.0.0")
public class ExamplePluginImpl implements ExamplePlugin {

    @Override
    public String processData(String input) {
        return "Processed: " + input;
    }
}
```

## 配置参考

MCP的配置项以`mcp`为前缀，支持多级配置。

```yaml
mcp:
  enabled: true              # 全局开关
  
  # 功能模块配置
  features:
    weather:
      enabled: true          # 天气模块开关
      api-key: YOUR_API_KEY  # API密钥
      ttl: 3600              # 缓存时间(秒)
    
    documentation:
      enabled: true          # 文档模块开关
      storage-path: /data/docs  # 存储路径
```

## 开发规范

详细的开发规范请参考[开发规范](开发规范.md)文档。

## 模块依赖

模块间的依赖关系请参考[模块依赖说明](模块依赖说明.md)文档。

## 常见问题

### 如何添加新功能模块？

1. 在domain层定义接口
2. 在infrastructure层实现接口
3. 在starter中添加自动配置
4. 在配置中启用功能

### 如何替换默认实现？

提供同名接口的自定义实现，并使用@Primary注解：

```java
@Service
@Primary
public class CustomWeatherService implements WeatherService {
    // 自定义实现...
}
```

## 贡献指南

1. Fork 项目
2. 创建功能分支 (`git checkout -b feature/amazing-feature`)
3. 提交变更 (`git commit -m 'Add some amazing feature'`)
4. 推送到分支 (`git push origin feature/amazing-feature`)
5. 创建Pull Request

## 许可证

本项目采用 MIT 许可证 - 详情请参见 [LICENSE](LICENSE) 文件。

## 联系方式

- 作者：张三
- 邮箱：support@example.com
- GitHub：[https://github.com/yourusername](https://github.com/yourusername)