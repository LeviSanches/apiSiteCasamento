package com.sitecasamento.laislevi.adapters.output;

import com.sitecasamento.laislevi.application.core.domain.entities.ProductEntity;
import com.sitecasamento.laislevi.application.core.repository.ProductRepository;
import com.sitecasamento.laislevi.application.ports.output.ProductRepositoryOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ProductRepositoryAdapter implements ProductRepositoryOutputPort {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void salvar(ProductEntity productEntity) {
        productRepository.save(productEntity);
    }

    @Override
    public List<ProductEntity> buscarTodos() {
        return productRepository.findAll();
    }

    @Transactional
    @Override
    public int atualizarDisponibilidadeProduto(Long id) {
        return productRepository.updateAvailability(id);
    }


}
