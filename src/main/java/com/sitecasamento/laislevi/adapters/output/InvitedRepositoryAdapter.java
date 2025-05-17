package com.sitecasamento.laislevi.adapters.output;

import com.sitecasamento.laislevi.application.core.domain.entities.InvitedEntity;
import com.sitecasamento.laislevi.application.core.repository.InvitedRepositoy;
import com.sitecasamento.laislevi.application.ports.output.InvitedRepositoryOutputPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InvitedRepositoryAdapter implements InvitedRepositoryOutputPort {

    @Autowired
    private InvitedRepositoy invitedRepositoy;

    @Override
    public void salvar(InvitedEntity convidado) {
        invitedRepositoy.save(convidado);
    }

    @Override
    public void deletar(Long id) {
        invitedRepositoy.deleteById(id);
    }

    @Override
    public Optional<InvitedEntity> buscarPorId(Long id) {
        return invitedRepositoy.findById(id);
    }

    @Override
    public List<InvitedEntity> buscarTodos() {
        return invitedRepositoy.findAll();
    }

}
