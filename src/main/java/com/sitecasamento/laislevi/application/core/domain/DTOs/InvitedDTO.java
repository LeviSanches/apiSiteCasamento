package com.sitecasamento.laislevi.application.core.domain.DTOs;

import com.sitecasamento.laislevi.application.core.domain.entities.InvitedEntity;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class InvitedDTO {

    private Long id;

    private String nome;

    private String email;

    private Boolean statusPresenca;

    private List<EscortDTO> acompanhantes;

    public InvitedDTO() {}

    public InvitedDTO(InvitedEntity convidado) {
        BeanUtils.copyProperties(convidado, this);
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getStatusPresenca() {
        return statusPresenca;
    }

    public void setStatusPresenca(Boolean statusPresenca) {
        this.statusPresenca = statusPresenca;
    }

    public List<EscortDTO> getAcompanhantes() {
        return acompanhantes;
    }

    public void setAcompanhantes(List<EscortDTO> acompanhantes) {
        this.acompanhantes = acompanhantes;
    }
}
