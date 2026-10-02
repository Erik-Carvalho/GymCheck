package com.gymcheck.gymcheck.model;

import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "registros_treino")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistroTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "A data do treino é obrigatória.")
    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate data = LocalDate.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull(message = "A rotina do treino é obrigatória.")
    @JoinColumn(name = "rotina_id", nullable = false)
    private RotinaTreino rotina;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "registroTreino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Serie> series = new ArrayList<>();

    private String observacoesDia;
}