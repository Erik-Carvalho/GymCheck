package com.gymcheck.gymcheck.repository;

import com.gymcheck.gymcheck.model.RegistroTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RegistroTreinoRepository extends JpaRepository<RegistroTreino, Long> {
    List<RegistroTreino> findByUsuarioIdOrderByDataDesc(Long usuarioId);
    @EntityGraph(attributePaths = {"series", "series.exercicio"})
    List<RegistroTreino> findByUsuarioIdAndRotina_IdOrderByDataAsc(Long usuarioId, Long rotinaId);
    Optional<RegistroTreino> findByIdAndUsuarioId(Long id, Long usuarioId);
    List<RegistroTreino> findAllByOrderByDataDesc();
}