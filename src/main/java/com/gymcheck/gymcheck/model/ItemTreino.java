package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Entity
public class ItemTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome do exercício é obrigatório.")
    @Column(nullable = false)
    private String nomeExercicio;

    @Positive(message = "O número de séries deve ser maior que zero.")
    private Integer series;

    @Positive(message = "O número de repetições deve ser maior que zero.")
    private Integer repeticoes;

    @PositiveOrZero(message = "O peso deve ser maior ou igual a zero.")
    private Double peso;

    @Column(nullable = false, columnDefinition = "integer default 0")
    private Integer ordem = 0;

    @ManyToOne
    @JoinColumn(name = "rotina_treino_id")
    private RotinaTreino rotinaTreino;

    public ItemTreino() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNomeExercicio() { return nomeExercicio; }
    public void setNomeExercicio(String nomeExercicio) { this.nomeExercicio = nomeExercicio; }

    public Integer getSeries() { return series; }
    public void setSeries(Integer series) { this.series = series; }

    public Integer getRepeticoes() { return repeticoes; }
    public void setRepeticoes(Integer repeticoes) { this.repeticoes = repeticoes; }

    public Double getPeso() { return peso; }
    public void setPeso(Double peso) { this.peso = peso; }

    public Integer getOrdem() { return ordem; }
    public void setOrdem(Integer ordem) { this.ordem = ordem; }

    public RotinaTreino getRotinaTreino() { return rotinaTreino; }
    public void setRotinaTreino(RotinaTreino rotinaTreino) { this.rotinaTreino = rotinaTreino; }
}
