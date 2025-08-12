package com.algojudge.algojudge.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${onlinejudge.app.perplexity_base_url}")
    private String baseUrl;

    @Value("${onlinejudge.app.perplexity_api_key}")
    private String apiKey;

    @Bean
    public WebClient perplexityWebClient(){
        return WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeaders(headers -> {
                    headers.setBearerAuth(apiKey);
                    headers.setContentType(MediaType.APPLICATION_JSON);
                })
                .build();
    }
}
