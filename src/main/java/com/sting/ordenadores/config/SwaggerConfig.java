package com.sting.ordenadores.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .apiInfo(apiDetails())
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .build();
  }

  private ApiInfo apiDetails() {
    return new ApiInfo("Laptop API REST", "Admin all Laptops", "version:1.0", "https://www.google.com",
        new Contact("mikell", "https://github.com/mikellbobadilla", "bobadilla413@gmail.com"), "License MIT",
        "https://www.google.com", new ArrayList<>());
  }

}
