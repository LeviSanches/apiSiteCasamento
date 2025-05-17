package com.sitecasamento.laislevi.application.core.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MercadoPagoService {

    @Value("${mercado-pago.urls.url-mercado-pago}")
    private String baseUrl;
    @Value("${mercado-pago.api-token}")
    private String token;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + token)
                .build();
    }

    public String getPreference(String preferenceId) {
        return webClient.get()
            .uri("/checkout/preferences/" + preferenceId)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
