package com.sitecasamento.laislevi.application.core.domain.entities;

import com.sitecasamento.laislevi.application.core.domain.DTOs.InvitedDTO;
import jakarta.persistence.*;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "convidado")
public class InvitedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Garante a geração automática do ID
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Boolean statusPresenca;

    @OneToMany(mappedBy = "convidado", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EscortEntity> acompanhantes = new ArrayList<>();

    public InvitedEntity() {}

    public InvitedEntity(InvitedDTO invitedDTO) {
        BeanUtils.copyProperties(invitedDTO, this);
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

    public List<EscortEntity> getAcompanhantes() {
        return acompanhantes;
    }

    public void setAcompanhantes(List<EscortEntity> acompanhantes) {
        this.acompanhantes = acompanhantes;
    }

    public void addAcompanhante(EscortEntity acompanhante) {
        acompanhante.setConvidado(this);
        this.acompanhantes.add(acompanhante);
    }
}
