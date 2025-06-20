package com.sitecasamento.laislevi.application.core.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MercadoPagoService {

    @Value("${mercado-pago.urls.url-mercado-pago}")
    private String baseUrl;
    @Value("${mercado-pago.api-token-prod}")
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

    public String getPaymentInfo(String paymentId) {
        return webClient.get()
            .uri("/v1/payments/" + paymentId)
            .retrieve()
            .bodyToMono(String.class)
            .block();
    }
}
