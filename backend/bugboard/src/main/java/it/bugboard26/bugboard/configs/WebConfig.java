package it.bugboard26.bugboard.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import it.bugboard26.bugboard.auth.AuthInterceptor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/auth/login", "/error");
    }
}