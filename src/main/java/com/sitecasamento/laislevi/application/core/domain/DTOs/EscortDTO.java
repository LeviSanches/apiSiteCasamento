package com.sitecasamento.laislevi.application.core.domain.DTOs;

import com.sitecasamento.laislevi.application.core.domain.entities.EscortEntity;
import org.springframework.beans.BeanUtils;

public class EscortDTO {

    private Long id;

    private String nome;

    private InvitedDTO invitedDTO;

    public EscortDTO() {}

    public EscortDTO(EscortEntity acompanhante) {
        BeanUtils.copyProperties(acompanhante, this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public InvitedDTO getConvidadoDTO() {
        return invitedDTO;
    }

    public void setConvidadoDTO(InvitedDTO invitedDTO) {
        this.invitedDTO = invitedDTO;
    }
}
