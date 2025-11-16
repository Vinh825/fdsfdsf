package com.healthcare.patient.config;
import org.springframework.beans.factory.annotation.Value;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Value("${app.api-key.name}")
    private String apiKeyHeaderName;

    @Value("${app.api-key.value}")
    private String apiKeyValue;
    /**
     * Tạo Bean cho interceptor bạn vừa viết
     */
    @Bean
    public RequestInterceptor feignAuthRequestInterceptor() {


        return template -> {
            template.header(apiKeyHeaderName, apiKeyValue);
        };
    }}
