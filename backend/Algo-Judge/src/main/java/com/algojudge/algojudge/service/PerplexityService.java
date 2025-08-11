package com.algojudge.algojudge.service;

import com.algojudge.algojudge.dto.PerplexityRequestDTO;
import com.algojudge.algojudge.dto.PerplexityResponseDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PerplexityService {
    private final WebClient perWebClient;

    public PerplexityService(WebClient webClient) {
        this.perWebClient = webClient;
    }

    public String getAnswer(String query){
        PerplexityResponseDTO response = perWebClient.post()
                .uri("/chat/completions")
                .bodyValue(new PerplexityRequestDTO(query))
                .retrieve()
                .bodyToMono(PerplexityResponseDTO.class)
                .block();

        if(response != null && response.getChoices() != null && !response.getChoices().isEmpty()){
            return response.getChoices().get(0).getMessage().getContent();
        }
        return "Opps, something went wrong.";
    }

}
