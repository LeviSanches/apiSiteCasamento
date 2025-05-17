package com.sitecasamento.laislevi.adapters.input.controller;

import com.sitecasamento.laislevi.application.core.domain.DTOs.InvitedDTO;
import com.sitecasamento.laislevi.application.ports.input.InvitedInputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/convidado")
public class InvitedController {

    @Autowired
    private InvitedInputPort invitedInputPort;

    @GetMapping()
    public ResponseEntity<List<InvitedDTO>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(invitedInputPort.buscarTodos());
    }

    @PostMapping()
    public ResponseEntity<Void> insert(@RequestBody InvitedDTO convidado) {
        invitedInputPort.inserir(convidado);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) {
        invitedInputPort.deletar(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
