package com.sitecasamento.laislevi.application.config;

import com.sitecasamento.laislevi.application.core.services.MercadoPagoService;
import com.sitecasamento.laislevi.application.core.usecase.*;
import com.sitecasamento.laislevi.application.ports.output.InvitedRepositoryOutputPort;
import com.sitecasamento.laislevi.application.ports.output.PaymentRepositoryOutputPort;
import com.sitecasamento.laislevi.application.ports.output.ProductRepositoryOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigBeans {

    @Bean
    public InvitedUseCase insertConvidadoUseCase(InvitedRepositoryOutputPort invitedRepositoryOutputPort) {
        return new InvitedUseCase(invitedRepositoryOutputPort);
    }

    @Bean
    public ProductUseCase insertProdutoUseCase(ProductRepositoryOutputPort productRepositoryOutputPort) {
        return new ProductUseCase(productRepositoryOutputPort);
    }

    @Bean
    public PaymentUseCase insertPaymentUseCase(PaymentRepositoryOutputPort paymentRepositoryOutputPort, ProductUseCase insertProductUseCase) {
        return new PaymentUseCase(paymentRepositoryOutputPort, insertProductUseCase);
    }

    @Bean
    public MercadoPagoService mercadoPagoService() {
        return new MercadoPagoService();
    }
}
