package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

@Entity
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres.")
    @Column(nullable = false)
    private String senha;

    private String nome;

    @Past(message = "A data de nascimento deve ser uma data passada.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dataNascimento;

    private String sexo; // Masculino, Feminino, Outro

    @Positive(message = "A altura deve ser um valor positivo.")
    private Double altura; // Altura em cm (compatível também com alturaCm)

    // Construtores
    public Usuario() {}

    // Iniciais do usuário para o avatar
    public String getIniciais() {
        if (nome != null && !nome.trim().isEmpty()) {
            String[] partes = nome.trim().split("\\s+");
            if (partes.length >= 2) {
                return ("" + partes[0].charAt(0) + partes[partes.length - 1].charAt(0)).toUpperCase();
            } else {
                return partes[0].substring(0, Math.min(2, partes[0].length())).toUpperCase();
            }
        }
        if (email != null && !email.trim().isEmpty()) {
            return email.substring(0, Math.min(2, email.length())).toUpperCase();
        }
        return "GC";
    }

    public String getNomeExibicao() {
        if (nome != null && !nome.trim().isEmpty()) {
            return nome.trim();
        }
        if (email != null && !email.trim().isEmpty()) {
            int atIndex = email.indexOf('@');
            return (atIndex > 0) ? email.substring(0, atIndex) : email;
        }
        return "Atleta";
    }

    // Getters e Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) {
        this.email = (email != null) ? email.trim().toLowerCase() : null;
    }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public LocalDate getDataNascimento() { return dataNascimento; }
    public void setDataNascimento(LocalDate dataNascimento) { this.dataNascimento = dataNascimento; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public Double getAltura() { return altura; }
    public void setAltura(Double altura) { this.altura = altura; }

    // Métodos utilitários de compatibilidade para alturaCm
    public Double getAlturaCm() { return altura; }
    public void setAlturaCm(Double alturaCm) { this.altura = alturaCm; }
}
