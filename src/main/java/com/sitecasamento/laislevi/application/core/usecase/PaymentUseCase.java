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

public class PaymentUseCase implements PaymentInputPort {

    private final PaymentRepositoryOutputPort paymentRepositoryOutputPort;

    private final ProductUseCase insertProductUseCase;

    @Value("${mercado-pago.api-token}")
    private String tokenApi;
    @Value("${mercado-pago.urls.url-success}")
    private String urlsuccess;
    @Value("${mercado-pago.urls.url-pending}")
    private String urlpending;
    @Value("${mercado-pago.urls.url-failure}")
    private String urlfailure;
    private Long id;
    private String nome;
    private BigDecimal preco;
    private String categoria;
    private String imagem;
    private Integer quantidade;

    public PaymentUseCase(PaymentRepositoryOutputPort paymentRepositoryOutputPort, ProductUseCase insertProductUseCase) {
        this.paymentRepositoryOutputPort = paymentRepositoryOutputPort;
        this.insertProductUseCase = insertProductUseCase;
    }

    @Override
    public String criarPagamento(PaymentDTO paymentDTO) throws MPException, MPApiException {
        MercadoPagoConfig.setAccessToken(tokenApi);
        PreferenceClient client = new PreferenceClient();

        paymentDTO.getProdutos()
                .forEach(p -> {
                    this.id = p.getId();
                    this.nome = p.getNome();
                    this.imagem = p.getImagem();
                    this.quantidade = Integer.valueOf(p.getQuantidade());
                    this.categoria = p.getCategoria();
                    this.preco = BigDecimal.valueOf(p.getPreco());
                });

        PreferenceItemRequest itemRequest =
                PreferenceItemRequest.builder()
                .id(id.toString())
                .title(nome)
                .pictureUrl(imagem)
                .quantity(quantidade)
                .currencyId("BRL")
                .unitPrice(preco)
                .categoryId(categoria)
                .build();

        List<PreferenceItemRequest> items = new ArrayList<>();
        items.add(itemRequest);

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
                        .installments(5)
                        .defaultInstallments(1)
                        .build())

                .statementDescriptor("Mercado_Pago")
                .build();

        Preference preference = client.create(preferenceRequest);

        if (preference.getInitPoint() == null) {
            System.out.println("Erro: InitPoint está nulo");
        }

        System.out.println("Link para pagamento: " + preference.getInitPoint());
        System.out.println("Link para teste_sandbox " + preference.getSandboxInitPoint());
        System.out.println("Id: " + preference.getId());

        return preference.getInitPoint();

    }

    @Override
    public void inserirPagamento(PaymentDTO paymentDTO) {
        if (paymentDTO != null) {
            paymentRepositoryOutputPort.salvar(new PaymentEntity(paymentDTO));
            var idProduto = paymentDTO.getProdutos();
            idProduto
                    .forEach(p -> {
                        if (p.getId() != null && p.getPreco() > 500) {
                            insertProductUseCase.atualizarDisponibilidadeProduto(p.getId());
                        }
                    });
            return;
        }
        throw new InvalidArgumentException("Erro ao enviar dados para salvar no banco");
    }
}
