package com.gymcheck.gymcheck.repository;

import com.gymcheck.gymcheck.model.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.time.LocalDate;

@Repository
public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByUsuarioIdOrderByDataDescHorarioAsc(Long usuarioId);
    List<Refeicao> findByUsuarioIdAndDataOrderByHorarioAsc(Long usuarioId, LocalDate data);
    Optional<Refeicao> findByIdAndUsuarioId(Long id, Long usuarioId);
    List<Refeicao> findAllByOrderByHorarioAsc();
}