# MCP插件开发指南

## 1. 插件概述

MCP插件是MCP（模型上下文协议）系统的扩展模块，用于扩展MCP系统的功能。通过插件机制，可以在不修改MCP核心代码的情况下，添加新的功能。

## 2. 插件开发流程

### 2.1 创建Maven项目

创建一个Maven项目，并在pom.xml中添加以下依赖：

```xml
<dependencies>
    <!-- 依赖插件API -->
    <dependency>
        <groupId>com.wwj</groupId>
        <artifactId>mcp-plugin-api</artifactId>
    </dependency>
    
    <!-- 依赖插件核心 -->
    <dependency>
        <groupId>com.wwj</groupId>
        <artifactId>mcp-plugin-core</artifactId>
        <version>${project.version}</version>
    </dependency>
</dependencies>
```

### 2.2 实现插件接口

创建一个实现`McpPlugin`接口的类，示例：

```java
@Component
public class MyPlugin implements McpPlugin {

    private static final String PLUGIN_ID = "com.example.my-plugin";
    private static final String PLUGIN_NAME = "我的插件";
    private static final String PLUGIN_VERSION = "1.0.0";
    private static final String PLUGIN_DESCRIPTION = "这是一个示例插件";
    private static final String PLUGIN_AUTHOR = "开发者名称";

    @Override
    public String getPluginId() {
        return PLUGIN_ID;
    }

    @Override
    public String getName() {
        return PLUGIN_NAME;
    }

    @Override
    public String getVersion() {
        return PLUGIN_VERSION;
    }

    @Override
    public String getDescription() {
        return PLUGIN_DESCRIPTION;
    }

    @Override
    public String getAuthor() {
        return PLUGIN_AUTHOR;
    }

    @Override
    public void initialize() {
        // 插件初始化逻辑
    }

    @Override
    public void destroy() {
        // 插件销毁逻辑
    }
}
```

### 2.3 实现MCP工具

使用`@MCPTool`注解标记类为MCP工具，示例：

```java
@Slf4j
@Component
@MCPTool(
    name = "我的工具",
    description = "这是一个示例工具",
    version = "1.0.0",
    category = "示例分类"
)
public class MyMcpTool {

    @MCPToolOperation(
        name = "执行操作",
        description = "示例操作描述",
        parameters = {
            @MCPToolOperation.Parameter(
                name = "input",
                description = "输入参数",
                required = true,
                type = "String"
            )
        },
        returnDescription = "操作结果"
    )
    public String performAction(String input) {
        log.info("执行操作: {}", input);
        return "处理结果: " + input;
    }
}
```

### 2.4 添加配置信息

在`resources`目录下创建`META-INF/spring.factories`文件：

```properties
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
  com.example.plugin.config.MyPluginAutoConfiguration
```

创建自动配置类：

```java
@Configuration
@ComponentScan("com.example.plugin")
public class MyPluginAutoConfiguration {
    // 配置逻辑
}
```

## 3. 插件开发规范

### 3.1 命名规范

- 包名：com.wwj.plugin.{插件名}
- 插件ID：com.wwj.{插件名}
- 类名：使用驼峰命名法，如MyPlugin, MyService等

### 3.2 代码规范

- 使用Lombok简化代码
- 添加完整的JavaDoc文档
- 使用统一的日志框架（SLF4J + Lombok @Slf4j）
- 遵循阿里巴巴Java开发手册规范

### 3.3 接口设计规范

- 接口参数与返回值应使用简单的数据类型或DTO
- 异常处理：使用统一的异常体系
- 接口应提供完整的参数验证

## 4. 插件发布与部署

### 4.1 打包插件

使用Maven打包插件：

```bash
mvn clean package
```

### 4.2 部署插件

将打包好的JAR文件放到MCP系统的插件目录下：

```
{MCP_HOME}/plugins/{插件名}-{版本}.jar
```

### 4.3 启用插件

在MCP系统的配置文件中添加插件ID：

```yaml
mcp:
  plugins:
    enabled:
      - com.wwj.{插件名}
```

## 5. 插件测试

### 5.1 单元测试

为插件的核心功能编写单元测试，确保功能正确。

### 5.2 集成测试

在MCP环境中测试插件的功能，确保插件能够正常加载和使用。

## 6. 示例插件

可以参考`mcp-plugin-weather`项目作为示例，了解插件的完整实现。

## 7. 常见问题

### 7.1 插件无法加载

- 检查JAR包是否放在正确的目录
- 检查插件ID是否配置正确
- 查看MCP系统日志，了解具体错误

### 7.2 插件功能不可用

- 检查插件是否正确初始化
- 检查需要的依赖是否齐全
- 检查Spring Bean是否正确注册

## 8. 联系方式

如有问题，请联系MCP开发团队。