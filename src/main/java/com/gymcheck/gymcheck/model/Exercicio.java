package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exercicios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String grupoMuscular;

    private String observacoes;

    private Double pesoInicialKg = 0.0;
    private Double pesoAtualKg = 0.0;
    private Double recordePessoalKg = 0.0; // PR (Personal Record)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}