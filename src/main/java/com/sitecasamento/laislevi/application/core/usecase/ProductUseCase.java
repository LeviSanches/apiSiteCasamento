package com.sitecasamento.laislevi.application.core.usecase;

import com.sitecasamento.laislevi.application.core.domain.DTOs.ProductDTO;
import com.sitecasamento.laislevi.application.core.domain.entities.ProductEntity;
import com.sitecasamento.laislevi.application.core.exceptions.InvalidArgumentException;
import com.sitecasamento.laislevi.application.ports.input.ProductInputPort;
import com.sitecasamento.laislevi.application.ports.output.ProductRepositoryOutputPort;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class ProductUseCase implements ProductInputPort {

    private final ProductRepositoryOutputPort productRepositoryOutputPort;

    public ProductUseCase(ProductRepositoryOutputPort productRepositoryOutputPort) {
        this.productRepositoryOutputPort = productRepositoryOutputPort;
    }

    @Override
    public void inserir(List<ProductDTO> productDTO) {
        if (productDTO != null) {
            productDTO.stream()
                    .map(ProductEntity::new)
                    .forEach(p -> {
                        p.setId(null);
                        productRepositoryOutputPort.salvar(p);
                    });
        }
    }

    @Override
    public int atualizarDisponibilidadeProduto(Long id) {
        if (id != null) {
            return productRepositoryOutputPort.atualizarDisponibilidadeProduto(id);
        }
        throw new InvalidArgumentException("O ID passado para o UseCase veio nulo");
    }

    @Override
    public List<ProductDTO> buscarTodos() {
        List<ProductEntity> produtos = productRepositoryOutputPort.buscarTodos();
        if(!produtos.isEmpty()) {
            return produtos.stream()
                    .map(ProductDTO::new)
                    .toList();
        }
        throw new EntityNotFoundException("Erro ao buscar produtos");
    }
}
