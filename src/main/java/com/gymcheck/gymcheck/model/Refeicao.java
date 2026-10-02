package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalTime;

@Entity
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da refeição é obrigatório.")
    private String nomeRefeicao;

    private LocalTime horario;

    @Column(columnDefinition = "TEXT")
    private String descricaoAlimentos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Refeicao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeRefeicao() { return nomeRefeicao; }
    public void setNomeRefeicao(String nomeRefeicao) { this.nomeRefeicao = nomeRefeicao; }

    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = horario; }

    public String getDescricaoAlimentos() { return descricaoAlimentos; }
    public void setDescricaoAlimentos(String descricaoAlimentos) { this.descricaoAlimentos = descricaoAlimentos; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
