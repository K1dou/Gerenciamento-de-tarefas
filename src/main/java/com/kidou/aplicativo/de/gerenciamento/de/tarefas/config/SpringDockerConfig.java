package com.kidou.aplicativo.de.gerenciamento.de.tarefas.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDockerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Rest API - Tarefas")
                                .description("API para gerenciar Tarefas")
                                .contact(new Contact().email("hique1276@gmail.com"))
                );
    }
}
