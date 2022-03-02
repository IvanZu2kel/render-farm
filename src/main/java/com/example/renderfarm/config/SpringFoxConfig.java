package com.example.renderfarm.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

/**
 * на данный момент свагер запускается по этой ссылке -> http://localhost:8080/swagger-ui.html
 */
@Configuration
@OpenAPIDefinition(info = @Info(title = "Products and lists service", version = "0.0.1 SNAPSHOT"))
public class SpringFoxConfig {
}
