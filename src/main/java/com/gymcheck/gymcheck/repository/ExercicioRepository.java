package com.gymcheck.gymcheck.repository;

import com.gymcheck.gymcheck.model.Exercicio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExercicioRepository extends JpaRepository<Exercicio, Long> {
    List<Exercicio> findByUsuarioIdOrderByNomeAsc(Long usuarioId);
    Optional<Exercicio> findFirstByUsuarioIdAndNomeIgnoreCase(Long usuarioId, String nome);
    List<Exercicio> findByGrupoMuscularIgnoreCase(String grupoMuscular);
}