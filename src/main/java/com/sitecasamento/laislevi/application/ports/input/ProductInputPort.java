package com.sitecasamento.laislevi.application.ports.input;

import com.sitecasamento.laislevi.application.core.domain.DTOs.ProductDTO;

import java.util.List;

public interface ProductInputPort {

    void inserir(List<ProductDTO> produto);

    int atualizarDisponibilidadeProduto(Long id);

    List<ProductDTO> buscarTodos();

}
