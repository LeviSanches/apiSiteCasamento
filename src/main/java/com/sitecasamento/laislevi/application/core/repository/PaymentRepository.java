package com.sitecasamento.laislevi.application.core.repository;

import com.sitecasamento.laislevi.application.core.domain.entities.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
