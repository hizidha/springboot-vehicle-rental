package com.devland.assignment.assignment9.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    OpenAPI app() {
        Info info = new Info();
        info.setTitle("Vehicle Rental");
        info.setDescription("Group Assignment - 9");
        info.setVersion("1.0.0");

        return new OpenAPI().info(info);
    }
}