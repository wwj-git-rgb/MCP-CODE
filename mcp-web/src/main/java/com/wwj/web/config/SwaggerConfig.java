package com.wwj.web.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 *
 * @author wenjie
 * @since 1.0.0
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI mcpOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("MCP API")
                        .description("MCP平台API文档")
                        .version("v1.0.0")
                        .contact(new Contact().name("MCP Team").email("support@example.com"))
                        .license(new License().name("Apache 2.0").url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("MCP 外部文档")
                        .url("https://github.com/wwj-git-rgb/MCP-CODE"));
    }
}