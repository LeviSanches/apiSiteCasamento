package com.sitecasamento.laislevi.application.core.usecase;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.common.PhoneRequest;
import com.mercadopago.client.preference.*;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.sitecasamento.laislevi.application.core.domain.DTOs.PaymentDTO;
import com.sitecasamento.laislevi.application.core.domain.entities.PaymentEntity;
import com.sitecasamento.laislevi.application.core.exceptions.InvalidArgumentException;
import com.sitecasamento.laislevi.application.ports.input.PaymentInputPort;
import com.sitecasamento.laislevi.application.ports.output.PaymentRepositoryOutputPort;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PaymentUseCase implements PaymentInputPort {

    private static final Logger logger = Logger.getLogger(PaymentUseCase.class.getName());

    private final PaymentRepositoryOutputPort paymentRepositoryOutputPort;

    private final ProductUseCase insertProductUseCase;

    @Value("${mercado-pago.api-token-prod}")
    private String tokenApi;
    @Value("${mercado-pago.urls.url-success}")
    private String urlsuccess;
    @Value("${mercado-pago.urls.url-pending}")
    private String urlpending;
    @Value("${mercado-pago.urls.url-failure}")
    private String urlfailure;

    public PaymentUseCase(PaymentRepositoryOutputPort paymentRepositoryOutputPort, ProductUseCase insertProductUseCase) {
        this.paymentRepositoryOutputPort = paymentRepositoryOutputPort;
        this.insertProductUseCase = insertProductUseCase;
    }

    @Override
    public String criarPagamento(PaymentDTO paymentDTO) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(tokenApi);
        PreferenceClient client = new PreferenceClient();

        List<PreferenceItemRequest> items = new ArrayList<>();

        paymentDTO.getProdutos().forEach(
                p -> {
                    PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                            .id(p.getId().toString())
                            .title(p.getNome())
                            .pictureUrl(p.getImagem())
                            .quantity(Integer.valueOf(p.getQuantidade()))
                            .currencyId("BRL")
                            .unitPrice(BigDecimal.valueOf(p.getPreco()))
                            .categoryId(p.getCategoria())
                            .build();
                    items.add(itemRequest);
                }
        );

        List<PreferencePaymentTypeRequest> excludedPaymentTypes = new ArrayList<>();
        excludedPaymentTypes.add(PreferencePaymentTypeRequest.builder().id("ticket").build());

        List<PreferencePaymentMethodRequest> excludedPaymentMethods = new ArrayList<>();
        excludedPaymentMethods.add(PreferencePaymentMethodRequest.builder().id("pec").build());

        PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                .autoReturn("all")
                .backUrls(PreferenceBackUrlsRequest.builder()
                        .success(urlsuccess)
                        .failure(urlfailure)
                        .pending(urlpending)
                        .build())
                .differentialPricing(PreferenceDifferentialPricingRequest.builder()
                        .id(1L)
                        .build())
                .externalReference(items.get(0).getId())
                .expires(false)
                .items(items)
                .marketplaceFee(new BigDecimal("0"))
                .payer(PreferencePayerRequest.builder()
                        .name(paymentDTO.getNomeConvidado())
                        .email(paymentDTO.getEmail())
                        .phone(PhoneRequest.builder()
                                .areaCode(paymentDTO.getTelefone().substring(0, 2))
                                .number(paymentDTO.getTelefone().substring(2))
                                .build())
                        .build())
                .additionalInfo(paymentDTO.getMensagem())
                .binaryMode(true)
                .marketplace("marketplace")
                .operationType("regular_payment")
                .paymentMethods(PreferencePaymentMethodsRequest.builder()
                        .defaultPaymentMethodId("master")
                        .excludedPaymentTypes(excludedPaymentTypes)
                        .excludedPaymentMethods(excludedPaymentMethods)
                        .installments(12)
                        .defaultInstallments(1)
                        .build())

                .statementDescriptor("Mercado_Pago")
                .build();

        Preference preference = client.create(preferenceRequest);

        if (preference.getInitPoint() == null) {
            System.out.println("Erro: InitPoint está nulo");
        }

        logger.info("Link para pagamento: "+ preference.getInitPoint());
        logger.info("preferenceId: "+ preference.getId());

        return preference.getInitPoint();

    }

    @Override
    public void inserirPagamento(PaymentDTO paymentDTO) {
        if (paymentDTO != null) {
            logger.info("salvando pagamento...");
            paymentRepositoryOutputPort.salvar(new PaymentEntity(paymentDTO));
            var idProduto = paymentDTO.getProdutos();
            idProduto
                    .forEach(p -> {
                        if (p.getId() != null) {
                            insertProductUseCase.atualizarDisponibilidadeProduto(p.getId());
                        }

                    });
            return;
        }
        logger.info("erro ao salvar pagamento...");
        throw new InvalidArgumentException("Erro ao enviar dados para salvar no banco");
    }
}
