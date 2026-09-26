package com.gymcheck.gymcheck.repository;

import com.gymcheck.gymcheck.model.Refeicao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefeicaoRepository extends JpaRepository<Refeicao, Long> {
    List<Refeicao> findByUsuarioIdOrderByHorarioAsc(Long usuarioId);
    Optional<Refeicao> findByIdAndUsuarioId(Long id, Long usuarioId);
    List<Refeicao> findAllByOrderByHorarioAsc();
}