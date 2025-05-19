# MCP - Model Context Protocol 实现

## 项目简介

MCP是一个基于Model Context Protocol规范实现的Java开发框架，旨在简化AI模型与工具之间的交互。本项目采用DDD领域驱动设计和整洁架构，实现了可插拔式架构，支持灵活的扩展。

## 架构设计

项目采用模块化设计，每个模块都有明确的职责，遵循单一职责原则和依赖倒置原则。

### 模块结构

- **mcp-bom**: 依赖管理模块
- **mcp-common**: 公共组件和工具类
- **mcp-domain**: 领域模型定义
- **mcp-infrastructure**: 基础设施实现
- **mcp-application**: 应用服务
- **mcp-web**: Web控制层
- **mcp-spring**: Spring Boot自动配置
- **mcp-test**: 测试支持
- **mcp-plugin-api**: 插件API接口
- **mcp-plugin-core**: 插件核心机制
- **mcp-plugin-weather**: 天气插件示例

### 核心组件

1. **工具系统 (Tool)**
   - 定义了统一的工具接口
   - 支持参数定义和检验
   - 提供同步和异步调用模式

2. **插件系统 (Plugin)**
   - 生命周期管理
   - 动态发现和加载
   - 可插拔扩展机制

3. **自动配置**
   - 具备条件化配置特性
   - 按需启用功能模块
   - 支持外部属性配置

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- Spring Boot 3.2.0+

### 构建项目

```bash
mvn clean install
```

### 使用示例

#### 1. 添加依赖

```xml
<dependency>
    <groupId>com.wwj</groupId>
    <artifactId>mcp-spring</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

#### 2. 配置属性

```yaml
mcp:
  enabled: true
  features:
    weather:
      enabled: true
      api-key: your-api-key
      provider: openweather
    documentation:
      enabled: true
      storage-path: /data/mcp/docs
  tools:
    executor-threads: 10
    enable-metrics: true
```

#### 3. 创建自定义工具

```java
package com.example.tool;

import com.wwj.domain.core.tool.ToolRequest;
import com.wwj.domain.core.tool.ToolResponse;
import com.wwj.plugin.api.annotation.MCPTool;
import com.wwj.plugin.api.annotation.Parameter;
import com.wwj.plugin.core.tool.AbstractTool;
import org.springframework.stereotype.Component;

@Component
@MCPTool(name = "calculator", description = "简单计算器")
public class CalculatorTool extends AbstractTool {

    @Parameter(name = "operation", description = "运算类型", required = true, enumValues = "[\"+\", \"-\", \"*\", \"/\"]")
    private String operation;
    
    @Parameter(name = "a", description = "第一个数字", required = true)
    private Double a;
    
    @Parameter(name = "b", description = "第二个数字", required = true)
    private Double b;
    
    @Override
    protected ToolResponse doExecute(ToolRequest request) {
        try {
            double result;
            switch (operation) {
                case "+":
                    result = a + b;
                    break;
                case "-":
                    result = a - b;
                    break;
                case "*":
                    result = a * b;
                    break;
                case "/":
                    if (b == 0) {
                        return ToolResponse.error("除数不能为零");
                    }
                    result = a / b;
                    break;
                default:
                    return ToolResponse.error("不支持的运算: " + operation);
            }
            return ToolResponse.success(result);
        } catch (Exception e) {
            return ToolResponse.error("计算错误: " + e.getMessage());
        }
    }
}
```

#### 4. 测试工具

```java
import com.wwj.domain.core.tool.ToolResponse;
import com.wwj.test.MCPTestContext;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorToolTest {

    @Test
    public void testCalculator() {
        // 创建测试上下文
        MCPTestContext context = new MCPTestContext();
        
        // 注册工具
        context.registerTool(new CalculatorTool());
        
        // 准备参数
        Map<String, Object> params = new HashMap<>();
        params.put("operation", "+");
        params.put("a", 10);
        params.put("b", 5);
        
        // 调用工具
        ToolResponse response = context.invokeTool("calculator", params);
        
        // 验证结果
        assertThat(response.isSuccess()).isTrue();
        assertThat(response.getContent()).isEqualTo(15.0);
    }
}
```

## 插件开发

### 创建插件

1. 创建新的Maven模块
2. 添加依赖mcp-plugin-api和mcp-plugin-core
3. 实现PluginLifecycle接口
4. 创建和注册工具

### 插件示例

```java
@Component
public class MyPlugin implements PluginLifecycle {

    private final ToolRegistry toolRegistry;
    
    public MyPlugin(ToolRegistry toolRegistry) {
        this.toolRegistry = toolRegistry;
    }
    
    @Override
    public void initialize() {
        // 初始化插件
    }
    
    @Override
    public void start() {
        // 注册工具
        toolRegistry.registerTool(new MyCustomTool());
    }
    
    @Override
    public void stop() {
        // 取消工具注册
        toolRegistry.removeTool("my-tool");
    }
}
```

## 贡献指南

欢迎提交Issue和Pull Request，请遵循项目的代码规范和提交流程。

## 版本历史

- **0.1.0**: 初始版本，实现核心架构
- **0.2.0**: 增加插件系统
- **0.3.0**: 完善测试支持

## 许可证

MIT