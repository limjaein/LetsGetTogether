package com.lgt.config;

import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Value("${springdoc.version}")
    private String version;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
            .group("definition")
            .pathsToMatch("/api/**")
            .build();
    }

    @Bean
    public OpenAPI lgtOpenApi() {
        return new OpenAPI()
            .info(new Info().title("LGT API")
                .description("LGT Project's API document.")
                .version(version));
    }
}
