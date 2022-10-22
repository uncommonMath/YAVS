package yavs.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@SuppressWarnings("unused")
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedOrigins("http://localhost:4200");
        registry.addMapping("*")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedOrigins("http://localhost:4200");
    }
}
