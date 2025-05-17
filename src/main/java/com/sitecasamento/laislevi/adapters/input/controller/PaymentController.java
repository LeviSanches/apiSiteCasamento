package com.sitecasamento.laislevi.adapters.input.controller;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.sitecasamento.laislevi.application.core.domain.DTOs.PaymentDTO;
import com.sitecasamento.laislevi.application.core.services.MercadoPagoService;
import com.sitecasamento.laislevi.application.ports.input.PaymentInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/pagamento")
public class PaymentController {

    @Autowired
    private PaymentInputPort paymentInputPort;

    @Autowired
    private MercadoPagoService mercadoPagoService;

    @PostMapping
    public ResponseEntity<String> createPayment(@RequestBody PaymentDTO paymentDTO) throws MPException, MPApiException {
        if (paymentDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Dados inválidos");
        }

        try {
            var response = paymentInputPort.criarPagamento(paymentDTO);
            String jsonResponse = "{\"url\": \"" + response + "\"}";
            return ResponseEntity.status(HttpStatus.OK).body(jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    @PostMapping(value = "/save")
    public ResponseEntity<String> savePayment(@RequestBody PaymentDTO paymentDTO) {
        if (paymentDTO == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: Dados inválidos");
        }
        try {
            paymentInputPort.inserirPagamento(paymentDTO);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor: " + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<String> getStatusPayment(@RequestHeader String preferenceId) {
        if (preferenceId == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro: preferenceId inválida: " + preferenceId);
        }
        try {
            var response = mercadoPagoService.getPreference(preferenceId);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro interno no servidor: " + e.getMessage());
        }
    }

}
