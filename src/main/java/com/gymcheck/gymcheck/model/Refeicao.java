package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
public class Refeicao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da refeição é obrigatório.")
    private String nomeRefeicao;

    @NotNull(message = "A data da refeição é obrigatória.")
    @PastOrPresent(message = "A data da refeição não pode estar no futuro.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data = LocalDate.now();

    private LocalTime horario;

    @Column(columnDefinition = "TEXT")
    private String descricaoAlimentos;

    @DecimalMin(value = "0.0", message = "As calorias devem ser zero ou mais.")
    private Double calorias;

    @DecimalMin(value = "0.0", message = "A proteína deve ser zero ou mais.")
    private Double proteinasGramas;

    @DecimalMin(value = "0.0", message = "Os carboidratos devem ser zero ou mais.")
    private Double carboidratosGramas;

    @DecimalMin(value = "0.0", message = "As gorduras devem ser zero ou mais.")
    private Double gordurasGramas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Refeicao() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public String getNomeRefeicao() { return nomeRefeicao; }
    public void setNomeRefeicao(String nomeRefeicao) { this.nomeRefeicao = nomeRefeicao; }

    public LocalTime getHorario() { return horario; }
    public void setHorario(LocalTime horario) { this.horario = horario; }

    public String getDescricaoAlimentos() { return descricaoAlimentos; }
    public void setDescricaoAlimentos(String descricaoAlimentos) { this.descricaoAlimentos = descricaoAlimentos; }

    public Double getCalorias() { return calorias; }
    public void setCalorias(Double calorias) { this.calorias = calorias; }

    public Double getProteinasGramas() { return proteinasGramas; }
    public void setProteinasGramas(Double proteinasGramas) { this.proteinasGramas = proteinasGramas; }

    public Double getCarboidratosGramas() { return carboidratosGramas; }
    public void setCarboidratosGramas(Double carboidratosGramas) { this.carboidratosGramas = carboidratosGramas; }

    public Double getGordurasGramas() { return gordurasGramas; }
    public void setGordurasGramas(Double gordurasGramas) { this.gordurasGramas = gordurasGramas; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
