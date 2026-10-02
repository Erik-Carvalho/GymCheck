package com.gymcheck.gymcheck.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rotinas_treino")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RotinaTreino {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O nome da rotina é obrigatório.")
    @Column(nullable = false)
    private String nome; // Ex: "TREINO 1", "Treino A - Peito"

    private String descricao; // Ex: "Foco em hipertrofia"

    @ElementCollection(targetClass = DiaSemana.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "rotina_dias_semana", joinColumns = @JoinColumn(name = "rotina_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "dia_semana")
    private Set<DiaSemana> diasSemana = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @OneToMany(mappedBy = "rotinaTreino", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemTreino> itens = new ArrayList<>();
}
