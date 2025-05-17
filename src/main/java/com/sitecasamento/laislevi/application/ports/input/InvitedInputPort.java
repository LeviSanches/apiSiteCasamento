package com.sitecasamento.laislevi.application.ports.input;

import com.sitecasamento.laislevi.application.core.domain.DTOs.InvitedDTO;

import java.util.List;

public interface InvitedInputPort {

    void inserir(InvitedDTO invitedDTO);

    void deletar(Long id);

    List<InvitedDTO> buscarTodos();

}
