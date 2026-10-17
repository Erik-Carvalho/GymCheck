package com.gymcheck.gymcheck.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class UsuarioProfileDTO {

    @Size(max = 120, message = "O nome deve ter no máximo 120 caracteres.")
    private String nome;

    @Past(message = "A data de nascimento deve ser anterior à data atual.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    @Pattern(regexp = "^(|Masculino|Feminino|Outro)$", message = "Selecione uma opção de sexo válida.")
    private String sexo;

    @Positive(message = "A altura deve ser maior que zero.")
    @Max(value = 300, message = "A altura deve ser menor ou igual a 300 cm.")
    private Double altura;

    @Positive(message = "A meta de calorias deve ser maior que zero.")
    @Max(value = 15000, message = "A meta de calorias deve ser menor ou igual a 15000.")
    private Integer metaCaloriasDia;

    @Positive(message = "A meta de proteína deve ser maior que zero.")
    @Max(value = 1000, message = "A meta de proteína deve ser menor ou igual a 1000 g.")
    private Integer metaProteinasDia;

    @Positive(message = "A meta de carboidratos deve ser maior que zero.")
    @Max(value = 2000, message = "A meta de carboidratos deve ser menor ou igual a 2000 g.")
    private Integer metaCarboidratosDia;

    @Positive(message = "A meta de gorduras deve ser maior que zero.")
    @Max(value = 1000, message = "A meta de gorduras deve ser menor ou igual a 1000 g.")
    private Integer metaGordurasDia;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Integer getMetaCaloriasDia() { return metaCaloriasDia; }
    public void setMetaCaloriasDia(Integer metaCaloriasDia) { this.metaCaloriasDia = metaCaloriasDia; }

    public Integer getMetaProteinasDia() { return metaProteinasDia; }
    public void setMetaProteinasDia(Integer metaProteinasDia) { this.metaProteinasDia = metaProteinasDia; }

    public Integer getMetaCarboidratosDia() { return metaCarboidratosDia; }
    public void setMetaCarboidratosDia(Integer metaCarboidratosDia) { this.metaCarboidratosDia = metaCarboidratosDia; }

    public Integer getMetaGordurasDia() { return metaGordurasDia; }
    public void setMetaGordurasDia(Integer metaGordurasDia) { this.metaGordurasDia = metaGordurasDia; }
}