package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
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

    @NotBlank(message = "O nome do exercício é obrigatório.")
    @Column(nullable = false)
    private String nome;

    @NotBlank(message = "O grupo muscular é obrigatório.")
    @Column(nullable = false)
    private String grupoMuscular;

    private String observacoes;

    @PositiveOrZero(message = "O peso inicial deve ser maior ou igual a zero.")
    private Double pesoInicialKg = 0.0;

    @PositiveOrZero(message = "O peso atual deve ser maior ou igual a zero.")
    private Double pesoAtualKg = 0.0;

    @PositiveOrZero(message = "O recorde pessoal deve ser maior ou igual a zero.")
    private Double recordePessoalKg = 0.0; // PR (Personal Record)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;
}
