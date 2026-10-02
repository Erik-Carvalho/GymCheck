package com.gymcheck.gymcheck.repository;

import com.gymcheck.gymcheck.model.RotinaTreino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RotinaTreinoRepository extends JpaRepository<RotinaTreino, Long> {
    List<RotinaTreino> findByUsuarioIdOrderByOrdemAscIdAsc(Long usuarioId);
    Optional<RotinaTreino> findByIdAndUsuarioId(Long id, Long usuarioId);
}