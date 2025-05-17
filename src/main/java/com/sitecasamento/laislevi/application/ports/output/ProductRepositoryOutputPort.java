package com.sitecasamento.laislevi.application.ports.output;

import com.sitecasamento.laislevi.application.core.domain.entities.ProductEntity;

import java.util.List;

public interface ProductRepositoryOutputPort {

    void salvar(ProductEntity productEntity);

    List<ProductEntity> buscarTodos();

    int atualizarDisponibilidadeProduto(Long id);
}
