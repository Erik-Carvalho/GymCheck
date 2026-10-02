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
}