package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;

@Entity
public class ItemTreino {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nomeExercicio;

    private Integer series;
    private Integer repeticoes;
    private Double peso;

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

    public RotinaTreino getRotinaTreino() { return rotinaTreino; }
    public void setRotinaTreino(RotinaTreino rotinaTreino) { this.rotinaTreino = rotinaTreino; }
}