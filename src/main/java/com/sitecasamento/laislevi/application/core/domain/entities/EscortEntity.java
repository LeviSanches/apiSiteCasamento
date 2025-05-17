package com.sitecasamento.laislevi.application.core.domain.entities;

import com.sitecasamento.laislevi.application.core.domain.DTOs.EscortDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

@Entity
@Table(name = "acompanhante")
public class EscortEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String nome;

    @ManyToOne
    @JoinColumn(name = "convidado_id", nullable = false)
    private InvitedEntity convidado;

    public EscortEntity() {}

    public EscortEntity(EscortDTO escortDTO) {
        BeanUtils.copyProperties(escortDTO, this);
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

    public InvitedEntity getConvidado() {
        return convidado;
    }

    public void setConvidado(InvitedEntity convidado) {
        this.convidado = convidado;
    }


}
