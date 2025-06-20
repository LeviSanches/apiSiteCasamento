package com.sitecasamento.laislevi.application.core.services;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class NotificationService {

    @Value("${notification.url-bot-whatsapp}")
    private String baseUrl;
    @Value("${notification.number}")
    private String number;

    private WebClient webClient;

    @PostConstruct
    public void init() {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .build();
    }

    public void notify(String message) {
        Mensagem payload = new Mensagem(number, message);
        webClient.post()
                .uri("/enviar")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(payload)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(resposta -> System.out.println("✅ Resposta do servidor: " + resposta))
                .doOnError(erro -> System.err.println("❌ Erro: " + erro.getMessage()))
                .block();
    }

    static class Mensagem {
        private String numero;
        private String mensagem;

        public Mensagem(String numero, String mensagem) {
            this.mensagem = mensagem;
            this.numero = numero;
        }

        public String getNumero() {
            return numero;
        }

        public void setNumero(String numero) {
            this.numero = numero;
        }

        public String getMensagem() {
            return mensagem;
        }

        public void setMensagem(String mensagem) {
            this.mensagem = mensagem;
        }
    }
}
