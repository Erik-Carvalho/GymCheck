package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "series")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "registro_treino_id", nullable = false)
    private RegistroTreino registroTreino;

    @ManyToOne
    @JoinColumn(name = "exercicio_id", nullable = false)
    private Exercicio exercicio;

    private Integer numeroSerie;

    @Column(nullable = false)
    private Double pesoKg;

    @Column(nullable = false)
    private Integer repeticoes;
}