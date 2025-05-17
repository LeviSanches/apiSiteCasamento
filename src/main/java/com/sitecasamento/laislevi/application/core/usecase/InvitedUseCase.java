package com.sitecasamento.laislevi.application.core.usecase;

import com.sitecasamento.laislevi.application.core.domain.DTOs.EscortDTO;
import com.sitecasamento.laislevi.application.core.domain.DTOs.InvitedDTO;
import com.sitecasamento.laislevi.application.core.domain.entities.EscortEntity;
import com.sitecasamento.laislevi.application.core.domain.entities.InvitedEntity;
import com.sitecasamento.laislevi.application.core.exceptions.InvalidArgumentException;
import com.sitecasamento.laislevi.application.ports.input.InvitedInputPort;
import com.sitecasamento.laislevi.application.ports.output.InvitedRepositoryOutputPort;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public class InvitedUseCase implements InvitedInputPort {

    private final InvitedRepositoryOutputPort invitedRepositoryOutputPort;

    public InvitedUseCase(InvitedRepositoryOutputPort invitedRepositoryOutputPort) {
        this.invitedRepositoryOutputPort = invitedRepositoryOutputPort;
    }

    @Override
    public void inserir(InvitedDTO invitedDTO) {
        if (invitedDTO != null) {
            InvitedEntity convidado = new InvitedEntity(invitedDTO);
            for (EscortDTO escortDTO : invitedDTO.getAcompanhantes()) {
                if(!escortDTO.getNome().isBlank() || !escortDTO.getNome().isEmpty()) {
                    EscortEntity acompanhante = new EscortEntity(escortDTO);
                    convidado.addAcompanhante(acompanhante);
                }
            }
            invitedRepositoryOutputPort.salvar(convidado);
            return;
        }
        throw new InvalidArgumentException("Erro ao inserir convidado");
    }

    @Override
    public void deletar(Long id) {
        boolean existe = invitedRepositoryOutputPort.buscarPorId(id).isPresent();
        if (existe) {
            InvitedEntity convidado = invitedRepositoryOutputPort.buscarPorId(id).get();
            invitedRepositoryOutputPort.deletar(id);
            return;
        }
        throw new EntityNotFoundException("Erro ao excluir convidado com o id: " + id);
    }

    @Override
    public List<InvitedDTO> buscarTodos() {
        List<InvitedEntity> convidados = invitedRepositoryOutputPort.buscarTodos();
        if (!convidados.isEmpty()) {
            return convidados.stream()
                    .map(InvitedDTO::new)
                    .toList();
        }
        throw new EntityNotFoundException("Erro ao buscar convidados");
    }


}
