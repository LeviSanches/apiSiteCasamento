package com.sitecasamento.laislevi.application.ports.output;

import com.sitecasamento.laislevi.application.core.domain.entities.InvitedEntity;

import java.util.List;
import java.util.Optional;

public interface InvitedRepositoryOutputPort {

    void salvar(InvitedEntity convidado);

    void deletar(Long id);

    Optional<InvitedEntity> buscarPorId(Long id);

    List<InvitedEntity> buscarTodos();

}
