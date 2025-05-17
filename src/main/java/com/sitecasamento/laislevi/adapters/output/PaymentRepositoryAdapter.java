package com.sitecasamento.laislevi.adapters.output;

import com.sitecasamento.laislevi.application.core.domain.entities.PaymentEntity;
import com.sitecasamento.laislevi.application.core.repository.PaymentRepository;
import com.sitecasamento.laislevi.application.ports.output.PaymentRepositoryOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PaymentRepositoryAdapter implements PaymentRepositoryOutputPort {

    @Autowired
    private PaymentRepository paymentRepository;

    @Override
    public void salvar(PaymentEntity paymentEntity) {
        paymentRepository.save(paymentEntity);
    }
}
