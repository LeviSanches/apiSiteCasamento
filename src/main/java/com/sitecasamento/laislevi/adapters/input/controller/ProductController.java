package com.sitecasamento.laislevi.adapters.input.controller;

import com.sitecasamento.laislevi.application.core.domain.DTOs.ProductDTO;
import com.sitecasamento.laislevi.application.ports.input.ProductInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/produto")
public class ProductController {

    @Autowired
    private ProductInputPort productInputPort;

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody List<ProductDTO> produtos) {
        productInputPort.inserir(produtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ProductDTO>> findAll() {
        return new ResponseEntity<>(productInputPort.buscarTodos(), HttpStatus.OK);
    }
}
