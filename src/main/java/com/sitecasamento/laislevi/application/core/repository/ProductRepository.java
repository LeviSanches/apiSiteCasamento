package com.sitecasamento.laislevi.application.core.repository;

import com.sitecasamento.laislevi.application.core.domain.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    @Modifying
    @Query(value = "UPDATE produto SET disponivel = false WHERE id = :id", nativeQuery = true)
    int updateAvailability(@Param("id") Long id);

    @Query(value = "SELECT * FROM produto ORDER BY nome", nativeQuery = true)
    List<ProductEntity> findAllGroupByName();
}
