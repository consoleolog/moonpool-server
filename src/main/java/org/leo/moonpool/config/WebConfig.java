package org.leo.moonpool.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**") //어디 경로에 cors 설정할겁니까
//                .allowedMethods("GET","POST","PUT","DELETE","HEAD","OPTIONS")
//                .allowedOrigins("*");
//    }
@Override
public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
            .allowedOrigins("http://localhost:3000/","https://consoleolog.github.io/")
            .allowCredentials(true)
            .allowedHeaders("Origin", "Content-Type", "Accept", "Authorization","boundary");
    }
}
